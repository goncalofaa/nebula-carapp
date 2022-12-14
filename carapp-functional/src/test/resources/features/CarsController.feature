Feature: Functional Tests for cars endpoint

  Background:
    Given All Test data has been deleted

  Scenario: http post request to cars/admin endpoint
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModelDontChangeMe, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Database updated\"}" is received
    And A status code of 201 is received

  Scenario: http post request to cars/admin endpoint with incorrect data, exception caught and pretty response returned
    When A "post" request is made to "cars/admin" endpoint with a car being ", X5, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received

  Scenario: http post request to cars/admin endpoint with duplicate data, exception caught and pretty response returned
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModelDontChangeMe, 2022, 80000, 10000, black"
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModelDontChangeMe, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Car already exists\"}" is received
    And A status code of 409 is received

  Scenario: http delete request to cars/admin endpoint
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand, TestModelDontChangeMe, 2022, 80000, 10000, black"
    Then A delete request is made to the last inserted car
    And A status code of 204 is received

  Scenario: http delete request to cars/admin endpoint with wrong id
    When A delete request is made to "cars/admin/12312" endpoint
    Then A body of "{\"description\":\"Incorrect id provided\"}" is received
    And A status code of 400 is received

  Scenario: http delete request to cars/admin endpoint with no id
    When A delete request is made to "cars/admin" endpoint
    Then A body of "{\"description\":\"Incorrect id provided\"}" is received
    And A status code of 400 is received

  Scenario: http get request to cars/admin endpoint with params
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand1, TestModelDontChangeMe, 2022, 80000, 10000, black"
    When A get request is made to "cars/admin/?brand=TestBrand" endpoint
#    Then A body of "[{\"id\":\"\\(\\d+\\)$\",\"brand\":\"TestBrand\",\"model\":\"TestModel\",\"year\":2022,\"price\":80000,\"mileage\":10000,\"colour\":\"black\"}]" is received
    Then A list of 1 cars is received
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand2, TestModelDontChangeMe, 2022, 80000, 10000, black"
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand3, TestModelDontChangeMe, 2022, 80000, 10000, black"
    When A get request is made to "cars/admin/?model=TestModelDontChangeMe" endpoint
    Then A list of 3 cars is received
    And A status code of 200 is received

  Scenario: http get request to cars/admin endpoint with wrong params
    When A get request is made to "cars/admin/?brand=1" endpoint
    Then A body of "{\"description\":\"Incorrect query parameter provided\"}" is received
    And A status code of 400 is received

  Scenario: http get request to cars/admin endpoint
    When A get request is made to "cars/admin" endpoint
    And A status code of 200 is received

  Scenario: http put request to cars/admin endpoint
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand1, TestModelDontChangeMe, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Database updated\"}" is received
    When A get request is made to "cars/admin/?brand=TestBrand" endpoint
    Then A list of 1 cars is received
    When A "put" request is made to "cars/admin" endpoint with a car being "TestBrand1, TestModelDontChangeMe, 2022, 2000, 10000, black"
    Then A body of "{\"description\":\"Car Updated\"}" is received
    And A status code of 200 is received

  Scenario: http put request to cars/admin endpoint with incorrect data, exception caught and pretty response returned
    When A "post" request is made to "cars/admin" endpoint with a car being "TestBrand1, TestModelDontChangeMe, 2022, 80000, 10000, black"
    Then A body of "{\"description\":\"Database updated\"}" is received
    When A get request is made to "cars/admin/?brand=TestBrand" endpoint
    Then A list of 1 cars is received
    When A "put" request is made to "cars/admin" endpoint with a car being "TestBrand1, , 1000, 2000, 12222, black"
    Then A body of "{\"description\":\"Incorrect car data provided\"}" is received
    And A status code of 400 is received



