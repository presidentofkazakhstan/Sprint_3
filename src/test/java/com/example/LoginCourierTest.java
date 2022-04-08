package com.example;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    CourierCredentials credentials;
    CourierCredentials credentialsWithInvalidPassword;
    CourierCredentials credentialsWithoutLogin;
    RandomGenerator courierGenerator;
    CourierClient courierClient;
    Courier courier;
    int courierId;
    String invalidPassword = "invalidPassword";

    @Before
    public void setUp() {
        courierGenerator = new RandomGenerator();
        courierClient = new CourierClient();

        courier = new Courier(courierGenerator.courierLogin, courierGenerator.courierPassword, courierGenerator.courierFirstName);
        courierClient.create(courier);

        credentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
        credentialsWithoutLogin = new CourierCredentials(courier.getPassword());
        credentialsWithInvalidPassword = new CourierCredentials(courier.getLogin(), invalidPassword);
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Login courier with valid credential")
    public void courierLoginWithValidCredential() {
        ValidatableResponse loginResponse = courierClient.login(credentials);
        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");
        assertThat("Courier cannot login", statusCode, equalTo(SC_OK));
        assertThat("Courier cannot login", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Login courier with invalid credential")
    public void courierLoginWithInvalidCredential() {
        ValidatableResponse loginResponse = courierClient.login(credentialsWithInvalidPassword);
        int statusCode = loginResponse.extract().statusCode();
        String messageText = loginResponse.extract().path("message");
        assertThat("Success login, Account found", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Success login, Account found", messageText, is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier without login")
    public void courierLoginWithoutLogin() {
        ValidatableResponse loginResponse = courierClient.login(credentialsWithoutLogin);
        int statusCode = loginResponse.extract().statusCode();
        String messageText = loginResponse.extract().path("message");
        assertThat("Success login, Enough data to login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Success login, Enough data to login", messageText, is("Недостаточно данных для входа"));
    }
}
