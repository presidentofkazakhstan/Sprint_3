package com.example;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import io.qameta.allure.junit4.DisplayName;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class CreateCourierTest {

    CourierClient courierClient;
    Courier courier;
    Courier courierWithoutPassword;
    RandomGenerator courierGenerator;
    int courierId;

    @Before
    public void setUp() {
        courierGenerator = new RandomGenerator();
        courierClient = new CourierClient();
        courier = new Courier(courierGenerator.courierLogin, courierGenerator.courierPassword, courierGenerator.courierFirstName);
        courierWithoutPassword = new Courier(courierGenerator.courierLogin, courierGenerator.courierFirstName);
    }

    @After
    public void tearDown() {
        if (courierWithoutPassword.getPassword() != null) {
            ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
            courierId = loginResponse.extract().path("id");
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Create courier with valid credential")
    public void courierCreateWithValidCredential() {
        ValidatableResponse createResponse = courierClient.create(courier);
        int statusCode = createResponse.extract().statusCode();
        boolean isCreated = createResponse.extract().path("ok");

        assertThat("Courier cannot create", statusCode, equalTo(SC_CREATED));
        assertThat("Courier cannot create", isCreated, is(not(false)));
    }

    @Test
    @DisplayName("Create courier with repeated login")
    public void courierCreateWithRepeatedLogin() {
        ValidatableResponse firstCreateResponse = courierClient.create(courier);
        ValidatableResponse secondCreateResponse = courierClient.create(courier);
        int statusCode = secondCreateResponse.extract().statusCode();
        String textMessage = secondCreateResponse.extract().path("message");

        assertThat("Courier created", statusCode, equalTo(SC_CONFLICT));
        assertThat("Courier created", textMessage, is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Create courier without password")
    public void courierCreateWithoutPassword() {

        ValidatableResponse createResponse = courierClient.create(courierWithoutPassword);
        int statusCode = createResponse.extract().statusCode();
        String textMessage = createResponse.extract().path("message");

        assertThat("Courier created", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Courier created", textMessage, is("Недостаточно данных для создания учетной записи"));
    }
}
