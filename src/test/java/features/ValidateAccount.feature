Feature: Validate Account API
  Scenario: As an authorized user, I would like to login success to the system
    Given Username "dungnguyen1" and Password "Plschange1@1"
    When user call "loginAPI" with "post" http request
    Then the API call got success with status code is 200
