Feature: Functional Tests for cars endpoint
  Scenario: http request to cars/admin endpoint
    When A request is made to "cars/admin" endpoint with a car being "Goncalo, X5, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Database updated\"}" is received
    And A status code of 201 is received