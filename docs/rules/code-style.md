# コーディング規約

## 1. 一般原則

### 1.1 コードの明瞭性
- シンプルで読みやすいコードを優先する
- 過度な最適化よりも可読性を重視する
- 自己文書化コードを目指し、適切な変数名・関数名を選定する

### 1.2 一貫性
- プロジェクト全体で統一されたスタイルを維持する
- 既存のコードパターンに従う
- 個人の好みよりもチームの規約を優先する

### 1.3 DRY原則 (Don't Repeat Yourself)
- コードの重複を避け、共通処理は抽象化する
- 類似コードは共通の抽象化を検討する
- ただし、過度な抽象化による複雑化も避ける

## 2. Java コーディングスタイル

### 2.1 標準スタイル
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) に準拠する
- インデントは4スペースを使用
- 行の長さは最大100文字に制限

### 2.2 命名規則
- クラス名: UpperCamelCase (例: `AudioProcessor`)
- インターフェース名: UpperCamelCase、通常は形容詞 (例: `Convertible`)
- メソッド名: lowerCamelCase (例: `convertAudioFormat`)
- 変数名: lowerCamelCase (例: `audioFile`)
- 定数名: UPPER_SNAKE_CASE (例: `MAX_FILE_SIZE`)
- パッケージ名: 小文字のみ (例: `com.company.project.feature`)

### 2.3 インポート
- インポートは適切にグループ化し、以下の順序で記述する:
  1. java標準ライブラリ
  2. javax標準ライブラリ
  3. サードパーティライブラリ
  4. プロジェクト内のクラス
- 各グループ内はアルファベット順にソート
- ワイルドカードインポート (`import java.util.*`) は避ける

```java
// 正しいインポート例
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.company.project.audio.AudioConverter;
import com.company.project.util.FormatUtil;
```

### 2.4 ドキュメンテーション
- すべての公開API、クラス、メソッドにはJavadocを記述する
- メソッドのすべてのパラメータと戻り値を文書化する
- スローされる例外についても文書化する

```java
/**
 * 音声ファイルを指定されたフォーマットに変換する。
 *
 * @param inputFile 入力ファイルのパス
 * @param outputFormat 出力形式 (デフォルト: "mp3")
 * @return 変換後のファイルパス
 * @throws FileNotFoundException 入力ファイルが存在しない場合
 * @throws ConversionException 変換処理に失敗した場合
 */
public String convertAudio(String inputFile, String outputFormat) {
    // 実装...
}
```

### 2.5 アクセス修飾子
- メンバーは可能な限り制限的なアクセス修飾子を使用する
- フィールドは原則としてprivateにし、必要に応じてgetterとsetterを提供する
- 不変オブジェクトの作成を推奨し、可能な場合はfinalを使用する

## 3. プロジェクト固有のルール

### 3.1 エラー処理
- チェック例外とアンチェック例外を適切に使い分ける
- カスタム例外は `com.company.project.exception` パッケージに定義
- すべての例外は適切にログに記録する

### 3.2 ログ記録
- SLF4Jを使用したロギングを推奨
- システムの動作状況を把握できるよう、適切なログレベルを使用
- ログメッセージは情報を明確に伝える内容にする
- 個人情報や機密情報のログ出力は避ける

```java
// 正しいログ記録例
private static final Logger logger = LoggerFactory.getLogger(AudioProcessor.class);

public void processFile(String fileId) {
    logger.info("File processing started: {}", fileId);
    try {
        // 処理...
    } catch (Exception e) {
        logger.error("Conversion failed for file {}: {}", fileId, e.getMessage(), e);
        throw new ProcessingException("処理に失敗しました", e);
    }
}
```

### 3.3 テスト
- JUnit5とMockitoを使用したユニットテストを書く
- テストクラス名は対象クラス名に `Test` を付与する
- モックを適切に使用して外部依存を分離する
- 重要なユースケースには結合テストとE2Eテストを用意

### 3.4 非同期処理
- CompletableFutureを使用した非同期プログラミング
- ExecutorServiceを適切に設定して使用
- 適切なタイムアウト処理を実装

## 4. コード品質ツール

以下のツールを用いて継続的にコード品質を確保する:

- **Checkstyle**: コーディング規約の遵守チェック
- **SpotBugs**: バグパターンの静的検出
- **PMD**: コード品質分析
- **JaCoCo**: コードカバレッジ測定
- **SonarQube**: 総合的なコード品質管理
- **ErrorProne**: コンパイル時のバグ検出

## 5. コードレビュー基準

コードレビューでは以下の点に注目する:

- 機能要件の充足
- コーディング規約の遵守
- アーキテクチャ設計原則の遵守
- テストの充実度
- エラー処理の適切さ
- パフォーマンスへの配慮
- セキュリティ上の懸念

## 6. バージョン管理

- コミットメッセージは明確で説明的に記述
- [Conventional Commits](https://www.conventionalcommits.org/) の形式に従う
- 機能単位でブランチを分け、完了後にプルリクエストを作成

```
feat: 音声ファイル変換機能の追加
fix: 大容量ファイル処理時のメモリリーク修正
docs: APIドキュメントの更新
test: 音声認識サービスのテストケース追加
```