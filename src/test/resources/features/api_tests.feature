Feature: Backend API Validation Suite

  @api
  Scenario: Verify complete products list API response
    Given I send a GET request to products list endpoint
    Then the status code should be 200
    And the response body should contain a list of products

  @api
  Scenario Outline: Verify user login API response codes
    Given I submit login API with email "<email>" and password "<password>"
    Then the response message should match expected result "<expected_code>"

    Examples:
      | email               | password     | expected_code |
      | nonexistent@qa.com  | WrongPass123 | 404           |
      | invalid_qa@test.com | WrongPass999 | 404           |