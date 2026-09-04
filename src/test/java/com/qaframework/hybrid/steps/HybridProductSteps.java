package com.qaframework.hybrid.steps;

import com.qaframework.hybrid.api.ApiClient;
import com.qaframework.hybrid.driver.DriverManager;
import com.qaframework.hybrid.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class HybridProductSteps {

    private final ApiClient apiClient = new ApiClient();
    private ProductsPage productsPage;
    private Response apiResponse;
    private String apiProductName;

    // ==========================================
    // 1. HYBRID STEPS
    // ==========================================

    @Given("I fetch all products using the backend API")
    public void i_fetch_all_products_using_the_backend_api() {
        apiResponse = apiClient.getAllProductsList();
    }

    @Then("the API response status code should be {int}")
    public void the_api_response_status_code_should_be(Integer expectedStatusCode) {
        Assert.assertEquals(apiResponse.getStatusCode(), (int) expectedStatusCode);
    }

    @Then("I extract the first product name from the API response")
    public void i_extract_the_first_product_name_from_the_api_response() {
        JsonPath json = apiResponse.jsonPath();
        apiProductName = json.getString("products[0].name");
        Assert.assertNotNull(apiProductName, "Product name extracted from API response was null!");
    }

    @When("I navigate to the products page on the UI")
    public void i_navigate_to_the_products_page_on_the_ui() {
        productsPage = new ProductsPage(DriverManager.getDriver());
        productsPage.navigateToProducts();
    }

    @When("I search for the product on the UI")
    public void i_search_for_the_product_on_the_ui() {
        productsPage.searchProduct(apiProductName);
    }

    @Then("the UI product list should contain the product name from the API")
    public void the_ui_product_list_should_contain_the_product_name_from_the_api() {
        List<String> visibleProductNames = productsPage.getAllVisibleProductNames();
        boolean matchFound = visibleProductNames.stream()
                .anyMatch(name -> name.equalsIgnoreCase(apiProductName));
        Assert.assertTrue(matchFound, "Product from API [" + apiProductName + "] was not displayed on the UI!");
    }

    // ==========================================
    // 2. PURE API STEPS
    // ==========================================

    @Given("I send a GET request to products list endpoint")
    public void i_send_a_get_request_to_products_list_endpoint() {
        apiResponse = apiClient.getAllProductsList();
    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer code) {
        Assert.assertEquals(apiResponse.getStatusCode(), (int) code);
    }

    @Then("the response body should contain a list of products")
    public void the_response_body_should_contain_a_list_of_products() {
        int productCount = apiResponse.jsonPath().getList("products").size();
        Assert.assertTrue(productCount > 0, "No products found in response!");
    }

    @Given("I submit login API with email {string} and password {string}")
    public void i_submit_login_api_with_email_and_password(String email, String password) {
        apiResponse = apiClient.verifyLogin(email, password);
    }

    @Then("the response message should match expected result {string}")
    public void the_response_message_should_match_expected_result(String expectedCode) {
        int responseCode = apiResponse.jsonPath().getInt("responseCode");
        Assert.assertEquals(String.valueOf(responseCode), expectedCode);
    }

    // ==========================================
    // 3. PURE UI STEPS
    // ==========================================

    @Given("I open the home page")
    public void i_open_the_home_page() {
        // Base URL loaded automatically by Hooks @ui
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String titleText) {
        Assert.assertTrue(DriverManager.getDriver().getTitle().contains(titleText));
    }

    @When("I search for {string} on the UI")
    public void i_search_for_item_on_the_ui(String item) {
        if (productsPage == null) {
            productsPage = new ProductsPage(DriverManager.getDriver());
        }
        productsPage.searchProduct(item);
    }

    @Then("the UI product list should contain the product name {string}")
    public void the_ui_product_list_should_contain_the_product_name(String itemName) {
        List<String> results = productsPage.getAllVisibleProductNames();
        boolean exists = results.stream().anyMatch(name -> name.toLowerCase().contains(itemName.toLowerCase()));
        Assert.assertTrue(exists, "Expected item not displayed: " + itemName);
    }
}