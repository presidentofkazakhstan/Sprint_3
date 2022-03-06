package com.example;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    static Order orderWithBlackColor;
    static Order orderWithBlackAndGreyColor;
    static Order orderWithoutColor;

    RandomGenerator clientGenerator;

    OrderClient orderClient;
    ArrayList<String> colors = new ArrayList<>();
    static int expectedStatusCode;

    @Before
    public void setUp()
    {
         orderClient = new OrderClient();
         clientGenerator = new RandomGenerator();
    }

    public CreateOrderTest(Order order,int expectedStatusCode) {
        this.orderWithBlackColor = order;
        this.orderWithBlackAndGreyColor = order;
        this.orderWithoutColor = order;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[][] orderCreate() {
        return new Object[][] {
                {orderWithBlackColor,  201},
                {orderWithBlackAndGreyColor,  201},
                {orderWithoutColor,  201},
        };
    }

    @Test
    public void createOrder()
    {
        colors.add("Black");
        orderWithBlackColor = new Order(clientGenerator.clientFirstName, clientGenerator.clientLastName, clientGenerator.clientAddress ,
                clientGenerator.clientMetroStation, clientGenerator.clientPhone, clientGenerator.rentTime,
                clientGenerator.deliveryDate, clientGenerator.comment , colors);

        ValidatableResponse createResponse = orderClient.create(orderWithBlackColor);
        int statusCode = createResponse.extract().statusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Test
    public void createOrderWithTwoColor()
    {
        colors.add("Black");
        colors.add("GREY");
        orderWithBlackAndGreyColor = new Order(clientGenerator.clientFirstName, clientGenerator.clientLastName, clientGenerator.clientAddress ,
                clientGenerator.clientMetroStation, clientGenerator.clientPhone, clientGenerator.rentTime,
                clientGenerator.deliveryDate, clientGenerator.comment , colors);

        ValidatableResponse createResponse = orderClient.create(orderWithBlackAndGreyColor);
        int statusCode = createResponse.extract().statusCode();
        assertEquals(expectedStatusCode, statusCode);
    }

    @Test
    public void createOrderWithoutColor()
    {
        orderWithoutColor = new Order(clientGenerator.clientFirstName, clientGenerator.clientLastName, clientGenerator.clientAddress ,
                clientGenerator.clientMetroStation, clientGenerator.clientPhone, clientGenerator.rentTime,
                clientGenerator.deliveryDate, clientGenerator.comment);

        ValidatableResponse createResponse = orderClient.create(orderWithoutColor);
        int statusCode = createResponse.extract().statusCode();
        assertEquals(expectedStatusCode, statusCode);
    }
}
