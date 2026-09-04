Feature: Real-Time Hybrid Product Verification

  @hybrid
  Scenario: Verify product fetched from backend API matches frontend UI catalog
    Given I fetch all products using the backend API
    Then the API response status code should be 200
    And I extract the first product name from the API response
    When I navigate to the products page on the UI
    And I search for the product on the UI
    Then the UI product list should contain the product name from the API