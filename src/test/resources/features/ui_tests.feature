Feature: Frontend UI Functional Suite

  @ui
  Scenario Outline: Search multiple product categories on UI
    Given I navigate to the products page on the UI
    When I search for "<item>" on the UI
    Then the UI product list should contain the product name "<item>"

    Examples:
      | item   |
      | Top    |
      | TShirt |
      | Jean   |
      | Dress  |

  @ui
  Scenario: Verify home page title
    Given I open the home page
    Then the page title should contain "Automation Exercise"