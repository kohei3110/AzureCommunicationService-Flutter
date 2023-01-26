# Azure Communication Services with Flutter App

## アーキテクチャ

<img src="images/architecture-ja.png" />

## コンテンツ

| File/folder | Description                           |
| ----------- | ------------------------------------- |
| backend     | バックエンド API ソースコード         |
| frontend    | モバイルアプリ (Flutter) ソースコード |
| images      | `README.md` で使われている画像        |

## How this sample works

発信者が電話をかけると、受信者にイベントが通知される。

<img src="images/callevent.png" />

## API Paths

| Method | Path            | Description                                                  |
| ------ | --------------- | ------------------------------------------------------------ |
| POST   | /api/users      | ユーザー新規作成                                             |
| GET    | /api/acs        | Azure Communication Services の userId と accessToken を取得 |
| GET    | /api/users/{id} | id をもとに ユーザー取得                                     |
| PUT    | /api/users      | ユーザー更新                                                 |
