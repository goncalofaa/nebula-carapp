Feature: Functional Tests for cars endpoint
  Background:
    Given All Test data has been deleted

  Scenario: http post request to cars/admin endpoint
    When A post request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Database updated\"}" is received
    And A status code of 201 is received

  Scenario: http get request to cars/admin endpoint
    When A get request is made to "cars/admin" endpoint
    And A status code of 200 is received

  Scenario: http delete request to cars/admin endpoint
    When A post request is made to "cars/admin" endpoint with a car being "TestBrand, TestModel, 2022, 80000, 10000, black"
    Then A delete request is made to the last inserted car
    And A status code of 204 is received

