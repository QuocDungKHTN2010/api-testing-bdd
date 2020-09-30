Feature: Get, Add, and Delete book

  Scenario: As user, I would to get all books in bookstore
    When user call "getBooksAPI" with "get" http request
    Then the API call got success with status code is 200

  @GetToken
  Scenario: As user, I want to borrow books
    Given a list isbn of books
      | isbn          |
      | 9781449325862 |
      | 9781449331818 |
    When user call "addBooksAPI" with "post" http request
    Then the API call got success with status code is 201

  @GetToken
  Scenario Outline: As user, I want to delete a book
    Given an isbn of book "<isbn>"
    When user call "deleteBookAPI" with "delete" http request
    Then the API call got success with status code is 204
    Examples:
      | isbn          |
      | 9781449325862 |
      | 9781449331818 |
