package com.example;

import org.apache.commons.lang3.RandomStringUtils;


public class RandomGenerator {

    public String courierLogin = RandomStringUtils.randomAlphabetic(10);

    public String courierPassword = RandomStringUtils.randomAlphabetic(10);

    public String courierFirstName = RandomStringUtils.randomAlphabetic(10);

    public String clientFirstName = RandomStringUtils.randomAlphabetic(10);

    public String clientLastName = RandomStringUtils.randomAlphabetic(10);

    public String clientAddress = RandomStringUtils.randomAlphabetic(10);

    public String clientMetroStation = RandomStringUtils.randomAlphabetic(10);

    public String clientPhone = RandomStringUtils.randomAlphabetic(10);

    public int rentTime = 1 + (int) (Math.random() * 10);

    public String deliveryDate = "2020-06-06";

    public String comment = RandomStringUtils.randomAlphabetic(10);

}
