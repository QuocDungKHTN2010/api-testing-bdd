Feature: Get, Add, and Delete book

  Scenario: As user, I would to get all books in bookstore
    When user call "getBooksAPI" with "get" http request
    Then the API call got success with status code is 200

  @GetToken @HealthyCheckTest
  Scenario: As user, I want to borrow books
    Given a list isbn of books
      | isbn          |
      | 9781449325862 |
      | 9781449331818 |
    When user call "addBooksAPI" with "post" http request
    Then the API call got success with status code is 201

  @GetToken @HealthyCheckTest
  Scenario Outline: As user, I want to delete a book
    Given an isbn of book "<isbn>"
    When user call "deleteBookAPI" with "delete" http request
    Then the API call got success with status code is 204
    Examples:
      | isbn          |
      | 9781449325862 |
      | 9781449331818 |

  @GetToken @HealthyCheckTest
  Scenario Outline: As user, I want to delete all books
    Given UserId "<userId>"
    When user call "deleteAllBooksAPI" with "delete" http request
    Then the API call got success with status code is 204
    Examples:
      | userId                               |
      | 50d02c25-7133-477c-97ca-e3782da569a7 |

  @GetToken @DeleteAllBooks @SmokeTest
  Scenario: As user, I want to borrow all books
    When user call "getBooksAPI" with "get" http request
    Then the API call got success with status code is 200
    When user collects all ISBN of books
    When user call "addBooksAPI" with "post" http request
    Then the API call got success with status code is 201

  @GetToken @DeleteAllBooks
  Scenario: As user, I want to borrow all books from exel file
    When user prepares payload from excel file
    When user call "addBooksAPI2" with "post" http request
    Then the API call got success with status code is 201