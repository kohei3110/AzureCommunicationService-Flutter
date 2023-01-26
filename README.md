# Azure Communication Services with Flutter App

## Architecture

<img src="images/architecture.png" />

## Contents

| File/folder | Description                      |
| ----------- | -------------------------------- |
| backend     | Backend API source code          |
| frontend    | Mobile app (Flutter) source code |
| images      | Images used in `README.md`       |

## How this sample works

When a caller starts calling, the callee gets events on his/her device.

<img src="images/callevent.png" />

## API Paths

| Method | Path            | Description                                             |
| ------ | --------------- | ------------------------------------------------------- |
| POST   | /api/users      | Create a new user                                       |
| GET    | /api/acs        | Get Azure Communication Services userId and accessToken |
| GET    | /api/users/{id} | Get a user by userId                                    |
| PUT    | /api/users      | Update a user                                           |
