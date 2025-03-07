# テスト駆動開発 (TDD) ガイドライン

## 1. TDDの基本サイクル

Farm Conversation Analyzerプロジェクトでは、以下のTDDサイクルを採用します：

### Red-Green-Refactor サイクル

1. **Red**: 失敗するテストを書く
   - 実装したい機能を明確にする
   - テストは実装前に記述し、必ず失敗することを確認する
   - テストは機能要件を明確に表現したものであること

2. **Green**: 最小限のコードで成功させる
   - テストが通るための最小限（必要十分）のコードを実装する
   - この段階ではパフォーマンスやエレガントさより機能の正しさを優先する

3. **Refactor**: リファクタリングする
   - コードの品質を高めるための改善を行う
   - テストは引き続き成功する状態を維持する
   - コードの重複を排除し、可読性を向上させる

## 2. テストの種類と役割

### 2.1 ユニットテスト

- 対象: クラス、メソッド
- 目的: コードの最小単位の正確性を検証
- 特徴: 高速に実行可能、外部依存をモック化
- ツール: JUnit 5, Mockito
- 場所: src/test/java 配下

```java
// 音声変換サービスのユニットテスト
@Test
@DisplayName("MP4からMP3への変換が成功すること")
void testConvertMp4ToMp3() {
    AudioConverter converter = new AudioConverter();
    ConversionResult result = converter.convert("test.mp4", "mp3");
    
    assertAll(
        () -> assertEquals("mp3", result.getFormat()),
        () -> assertTrue(result.isSuccess()),
        () -> assertTrue(Files.exists(result.getOutputPath()))
    );
}
```

### 2.2 統合テスト

- 対象: 複数のコンポーネントの相互作用
- 目的: コンポーネント間の連携が正しく機能することを確認
- 特徴: やや実行に時間がかかる、一部の外部依存を実際に使用
- 場所: `tests/integration` ディレクトリ

```java
@SpringBootTest
class FileUploadAndConversionTest {
    @Autowired private FileProcessingService fileService;
    @Autowired private AudioConversionService converterService;
    
    @Test
    void testFileUploadAndConversionFlow() {
        // テスト用ファイルをアップロード
        String fileId = fileService.upload(TEST_MP4_FILE);
        
        // 音声変換を実行
        ConversionResult result = converterService.processFile(fileId);
        
        // 検証
        assertAll(
            () -> assertEquals("completed", result.getStatus()),
            () -> assertTrue(storageService.exists(fileId + "/converted.mp3"))
        );
    }
}
```

### 2.3 エンドツーエンド (E2E) テスト

- 対象: システム全体の機能
- 目的: ユーザーの視点からのシナリオ検証
- 特徴: 実行に時間がかかる、実際のサービスと連携
- 場所: `tests/e2e` ディレクトリ

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FullAnalysisWorkflowTest {
    @Autowired private TestRestTemplate restTemplate;
    
    @Test
    void testFullAnalysisWorkflow() {
        // ファイルアップロード
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new FileSystemResource("test_samples/conversation.mp4"));
        ResponseEntity<JobResponse> response = restTemplate.postForEntity(
            "/api/files/upload", body, JobResponse.class);
        
        String jobId = response.getBody().getJobId();
        
        // 処理完了を待機
        ResultResponse result = waitForCompletion(jobId, Duration.ofMinutes(2));
        
        // 結果の検証
        assertAll(
            () -> assertEquals("completed", result.getStatus()),
            () -> assertNotNull(result.getExcelUrl()),
            () -> assertTrue(result.isNotificationSent())
        );
    }
}
```

## 3. テストカバレッジ方針

- **目標カバレッジ**: コードベース全体で80%以上
- **重要コンポーネント**: 核となるビジネスロジックは90%以上
- **測定**: JaCoCo
- **レポート**: Maven/Gradleビルド時に自動生成

## 4. モックとスタブの使用ガイドライン

### 4.1 モックを使用するケース

- 外部サービス（Azure Speech、Azure OpenAI等）の呼び出し
- データベース操作
- ファイルシステムアクセス
- 時間依存の処理

### 4.2 モック実装例

```java
// Azureサービスをモック化する例
@ExtendWith(MockitoExtension.class)
class SpeechServiceTest {
    @Mock private SpeechService speechService;
    
    @Test
    void testTranscribe() {
        when(speechService.transcribe(anyString())).thenReturn(
            new TranscriptionResult("こんにちは、具合はどうですか？", "veterinarian", 0.95));
        
        TranscriptionResult result = speechService.transcribe("test_audio.mp4");
        
        assertAll(
            () -> assertEquals("こんにちは、具合はどうですか？", result.getText()),
            () -> assertEquals("veterinarian", result.getSpeaker()),
            () -> assertEquals(0.95, result.getConfidence())
        );
    }
}
```

### 4.3 テストダブル選択指針

- **Stub**: 単純な戻り値が必要な場合
- **Mock**: 呼び出し回数や引数の検証が必要な場合
- **Fake**: 軽量な代替実装が必要な場合（インメモリDBなど）
- **Spy**: 実際の処理を行いつつ呼び出し情報も記録する場合

## 5. テストデータ管理

- テストデータは `tests/fixtures` ディレクトリに格納
- 大容量ファイルはGitに含めず、CIパイプラインで取得
- 機密データはテスト用に匿名化したものを使用
- フィクスチャとファクトリパターンを活用

```java
@TestConfiguration
public class TestDataConfig {
    @Bean
    public SampleConversationData sampleConversationData() {
        return new SampleConversationData(
            new Metadata("farm_123", LocalDate.of(2023, 5, 15), 630),
            List.of(
                new TranscriptEntry(0, "veterinarian", "今日はどのような症状がありますか？"),
                new TranscriptEntry(5, "farmer", "牛の食欲が昨日から落ちているんです")
            )
        );
    }
}
```

## 6. テスト自動化

### 6.1 ローカル開発環境

- コミット前に自動実行するプリコミットフック
- 変更されたコードに関連するテストのみ実行する機能

### 6.2 CI/CD パイプライン

- プルリクエスト時に全テストを自動実行
- テストカバレッジレポートの自動生成
- 失敗テストの通知とレポート

### 6.3 実行コマンド

```bash
# ユニットテストのみ実行
./mvnw test

# 統合テストを実行
./mvnw verify -P integration-test

# E2Eテストを実行
./mvnw verify -P e2e-test

# カバレッジレポート付きで全テスト実行
./mvnw verify -P all-tests jacoco:report
```

## 7. TDDのベストプラクティス

### 7.1 テストファーストの原則

- 必ず実装前にテストを記述する
- テストが意味のある失敗を示すことを確認してから実装に進む

### 7.2 FIRST原則

- **Fast**: テストは高速に実行できること
- **Independent**: テスト間に依存関係がないこと
- **Repeatable**: 何度実行しても同じ結果が得られること
- **Self-validating**: テストは自己検証可能であること
- **Timely**: テストは実装前に書くこと

### 7.3 テストの表現力

- テスト名は検証内容を明確に表現する
- Given-When-Then パターンで条件、操作、期待結果を明確に
- ヘルパー関数を使って複雑なセットアップを抽象化

```java
@Test
@DisplayName("大容量ファイルアップロード時にチャンク処理が適用されること")
void whenLargeFileUploadedThenChunkedProcessingUsed() {
    // Given: 大容量ファイルの準備
    File largeFile = TestFileUtil.createTestFile(500_000_000L); // 500MB
    
    // When: ファイル処理サービスに渡す
    FileProcessor processor = new FileProcessor();
    ProcessingResult result = processor.process(largeFile);
    
    // Then: チャンク処理が適用されていることを確認
    assertAll(
        () -> assertEquals(ProcessingStrategy.CHUNKED, result.getProcessingStrategy()),
        () -> assertTrue(result.getChunks().size() > 1)
    );
}
```

## 8. 特定のテスト戦略

### 8.1 非同期コードのテスト

```java
@Test
@DisplayName("非同期ファイル処理が成功すること")
void testAsyncFileProcessing() throws Exception {
    AsyncFileProcessor processor = new AsyncFileProcessor();
    CompletableFuture<ProcessingResult> future = processor.processFile("test.mp4");
    ProcessingResult result = future.get();
    
    assertEquals("completed", result.getStatus());
}
```

### 8.2 例外とエラー処理のテスト

```java
@Test
@DisplayName("無効なファイル形式が例外をスローすること")
void testInvalidFileFormatRaisesException() {
    AudioConverter converter = new AudioConverter();
    
    InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> {
        converter.convert("test.txt", "mp3");
    });
    
    assertTrue(exception.getMessage().contains("Unsupported format"));
}
```

### 8.3 分岐条件のテスト

各条件分岐を網羅するテストを作成し、すべてのパスが少なくとも1回は実行されるようにする。

## 9. コードレビューとテスト

コードレビューでは以下の点を重点的に確認します：

1. テストが機能要件を適切にカバーしているか
2. テスト自体の品質は適切か
3. エッジケースや例外パターンがテストされているか
4. テストの可読性と保守性は確保されているか

## 10. 継続的改善

- テスト戦略を定期的に見直し、改善する
- 重要な障害発生時には、該当するケースのテストを追加
- チーム内でテスト技術の共有と学習を促進