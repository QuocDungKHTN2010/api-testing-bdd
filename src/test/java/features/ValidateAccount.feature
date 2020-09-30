Feature: Validate Account API

  Scenario Outline: As a new user, I want to create an account to bookstore
    Given Username "<username>" and Password "<password>"
    When user call "createAccountAPI" with "post" http request
    Then the API call got success with status code is 201
    Examples:
      | username    | password     |
      | dungnguyen1 | Plschange1@1 |

  Scenario Outline: As an authorized user, I would like to login success to the system
    Given Username "<username>" and Password "<password>"
    When user call "loginAPI" with "post" http request
    Then the API call got success with status code is 200
    Examples:
      | username    | password     |
      | dungnguyen1 | Plschange1@1 |