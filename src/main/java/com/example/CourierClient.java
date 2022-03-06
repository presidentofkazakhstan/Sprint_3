package com.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient {

    private static final String COURIER_PATH_LOGIN = "https://qa-scooter.praktikum-services.ru/api/v1/courier/login";
    private static final String COURIER_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/courier/";

    @Step("Login with credentials {credentials}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH_LOGIN)
                .then();
    }

    @Step("Create courier with parameters {courier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Delete courier with id {courierId}")
    public ValidatableResponse delete(int courierId) {
        String str = Integer.toString(courierId);
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + str)
                .then();
    }
}
