Feature: Functional Tests for private endpoint
  Scenario: http get request to private/status endpoint
    When A get request is made to "private/status" endpoint
    Then A body of "OK" is received
    And A status code of 200 is received