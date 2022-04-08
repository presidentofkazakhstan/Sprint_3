package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetOrderListTest {

    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Ger order List")
    public void getOrderList() {
        ValidatableResponse getResponse = orderClient.getList();
        int statusCode = getResponse.extract().statusCode();
        ArrayList id = getResponse.extract().path("orders.id");
        assertThat("OrderList cannot get", statusCode, equalTo(SC_OK));
        assertThat("OrderList is Empty", id.isEmpty(), is(false));
    }
}
