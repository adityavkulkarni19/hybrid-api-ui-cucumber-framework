package com.qaframework.hybrid.api;

import com.qaframework.hybrid.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public ApiClient() {
        RestAssured.baseURI = ConfigReader.getProperty("api.base.url");
    }

    public Response getAllProductsList() {
        return given()
                .when()
                .get("/productsList")
                .then()
                .extract()
                .response();
    }

    public Response verifyLogin(String email, String password) {
        return given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post("/verifyLogin")
                .then()
                .extract()
                .response();
    }
}