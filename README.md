# Practice 2 — Spring Boot REST API 

본 프로젝트는 **도서(Book)**와 **리뷰(Review)** 도메인을 기반으로 RESTful API를 설계 및 구현하였습니다.

## 프로젝트 구조 
```text
src
 └── main
     └── java
         └── com.test.wsdpractice2
             ├── common             # 공통 모듈
             │   ├── config         # WebConfig (Interceptor 등록)
             │   ├── exception      # GlobalExceptionHandler, ErrorCode, CustomException
             │   ├── interceptor    # LoggingInterceptor (미들웨어)
             │   └── response       # ApiResponse, PageResponse (표준 응답)
             ├── book               # 도서 도메인 (Controller, Service, Repository, Entity, DTO)
             └── review             # 리뷰 도메인 (Controller, Service, Repository, Entity, DTO)
```

## 기술 스택

- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Build Tool**: Gradle
- **Database**: MySQL
- **Libraries**: Spring Data JPA, Lombok, Validation

## 표준 응답 포맷

### 성공 응답 예시

```json
{
  "isSuccess": true,
  "status": "200",
  "message": "도서 상세 조회 성공",
  "payload": {
    "bookId": 1,
    "title": "객체지향의 사실과 오해",
    "author": "조영호",
    "isbn": "978-11-1111"
  }
}
```

### 에러 응답 예시

```json
{
  "isSuccess": false,
  "status": "DUPLICATE_BOOK_ISBN",
  "message": "이미 등록된 도서(ISBN)입니다.",
  "payload": null
}
```

### 페이지네이션 응답 예시

```json
{
  "isSuccess": true,
  "status": "200",
  "message": "도서 목록 조회 성공",
  "payload": {
    "data": [
      {
        "bookId": 1,
        "title": "객체지향의 사실과 오해",
        "author": "조영호",
        "isbn": "978-11-1111"
      },
      {
        "bookId": 2,
        "title": "Real MySQL 8.0",
        "author": "백은빈",
        "isbn": "978-22-2222"
      }
    ],
    "meta": {
      "total": 2,
      "page": 1,
      "perPage": 10,
      "totalPages": 1
    },
    "links": {
      "self": "?page=1&size=10",
      "next": null,
      "prev": null
    }
  }
}
```

## 미들웨어

- **동작** : `LoggingInterceptor`가 모든 API 요청(`preHandle`)과 응답(`afterCompletion`)을 가로채어 로그를 출력
- **기능** : 요청 고유 ID(UUID) 부여, HTTP 메서드/URI 로깅, 처리 시간(Latency) 측정
- **로그 예시** : 
    ```text
    [REQUEST] [f9e8d7c6] POST /api/books
    [RESPONSE] [f9e8d7c6] POST /api/books | Status: 201 | Time: 15ms
    ```

## API 목록

### POST

**1. 도서 등록**

- **URL**: `POST /api/books`
- **Request Body**
  ```json
  {
    "title": "객체지향의 사실과 오해",
    "author": "조영호",
    "isbn": "978-11-1111"
  }
  ```
- **Success (201 Created)**
  - 정상적인 도서 정보 입력 시
- **Failure (400 Bad Request)**
  - INVALID_INPUT_VALUE: 제목, 저자, ISBN 중 빈 값이 있을 경우
  - DUPLICATE_BOOK_ISBN: 이미 등록된 ISBN으로 등록을 시도할 경우


**2. 리뷰 등록**

- **URL**: `POST /api/reviews`
- **Request Body**
  ```json
  {
    "bookId": 1,
    "reviewer": "student",
    "content": "최고의 책입니다.",
    "rating": 5
  }
  ```
- **Success (201 Created)**
  - 정상적인 리뷰 정보 입력 시
- **Failure (400 Bad Request)**
  - INVALID_INPUT_VALUE: 평점이 1~5 범위를 벗어나거나 내용이 비어있는 경우
  - DUPLICATE_REVIEW: 동일한 사용자가 같은 책에 이미 리뷰를 작성한 경우
- **Failure (404 Not Found)**
  - BOOK_NOT_FOUND: 존재하지 않는 책 ID(bookId)로 리뷰를 등록하려는 경우

### GET

**3. 도서 목록 조회 (Pagination)**

- **URL**: `GET /api/books?page=0&size=10&sort=id,desc`
- **Success (200 OK)**
  - 페이징 처리된 도서 목록 반환 (payload 내 data, meta, links 포함)

**4. 도서 단건 조회**

- **URL**: `GET /api/books/{bookId}`
- **Success (200 OK)**
  - 해당 ID의 도서 정보 반환
- **Failure (404 Not Found)**
  - BOOK_NOT_FOUND: 요청한 ID의 도서가 존재하지 않는 경우

**5. 리뷰 목록 조회 (Pagination)**

- **URL**: `GET /api/reviews?page=0&size=10`
- **Success (200 OK)**
  - 페이징 처리된 전체 리뷰 목록 반환

### PUT

**6. 도서 정보 수정**

- **URL**: `PUT /api/books/{bookId}`
- **Request Body** (변경할 필드만 포함 가능)
  ```json
  {
    "title": "객체지향의 사실과 오해 (개정판)"
  }
  ```
- **Success (200 OK)**
  - 제목, 저자, ISBN 중 변경된 필드만 수정 (Partial Update)
- **Failure (400 Bad Request)**
  - DUPLICATE_BOOK_ISBN: 수정하려는 ISBN이 이미 다른 책에서 사용 중인 경우
- **Failure (404 Not Found)**
  - BOOK_NOT_FOUND: 수정하려는 도서 ID가 없는 경우

**7. 리뷰 수정**

- **URL**: `PUT /api/reviews/{reviewId}`
- **Request Body**
  ```json
  {
    "content": "생각해보니 별로네요.",
    "rating": 2
  }
  ```
- **Success (200 OK)**
  - 리뷰 내용 또는 평점 수정
- **Failure (400 Bad Request)**
  - INVALID_INPUT_VALUE: 수정하려는 평점이 1~5 범위를 벗어난 경우
- **Failure (404 Not Found)**
  - REVIEW_NOT_FOUND: 수정하려는 리뷰 ID가 없는 경우

### DELETE

**8. 도서 삭제**

- **URL**: `DELETE /api/books/{bookId}`
- **Success (200 OK)**
  - 도서 삭제 성공
- **Failure (404 Not Found)**
  - BOOK_NOT_FOUND: 삭제하려는 도서 ID가 없는 경우

**9. 리뷰 삭제**

- **URL**: `DELETE /api/reviews/{reviewId}`
- **Success (200 OK)**
  - 리뷰 삭제 성공
- **Failure (404 Not Found)**
  - REVIEW_NOT_FOUND: 삭제하려는 리뷰 ID가 없는 경우