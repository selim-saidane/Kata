package com.bank.account.controller;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @LocalServerPort
    private int port;
    private RequestSpecification httpRequest;

    @Before
    public void init() throws IOException {
        //Given
        httpRequest = RestAssured.given();
    }

    @Test
    public void userShouldMakeDeposit() throws IOException {
        //Given
        createUser();
        String jsonDepositBody = generateStringFromResource("src\\test\\java\\com\\bank\\account\\payload\\deposit-withdrawal-payload.json");

        //When
        Response response = httpRequest.given()
                .body(jsonDepositBody)
                .header("Content-Type", "application/json")
                .when()
                .port(port)
                .request("POST", "/v1/bank/account/deposit");

        //Then
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void userShouldBeAbleToMakeWithdrawal() throws IOException {
        //Given
        createUser();
        String jsonWithdrawalBody = generateStringFromResource("src\\test\\java\\com\\bank\\account\\payload\\deposit-withdrawal-payload.json");

        //When
        Response response = httpRequest.given()
                .body(jsonWithdrawalBody)
                .header("Content-Type", "application/json")
                .when()
                .port(port)
                .request("POST", "/v1/bank/account/withdrawal");

        //Then
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void userShouldSeeHistoric() throws IOException {
        //When
        Response response = httpRequest.given()
                .header("Content-Type", "application/json")
                .when()
                .port(port)
                .request("GET", "/v1/bank/account/transactions/1");

        //Then
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(jsonPathEvaluator.get("amount"));
    }

    private void createUser() throws IOException {
        String jsonCreationBody = generateStringFromResource("src\\test\\java\\com\\bank\\account\\payload\\user-creation-payload.json");
        httpRequest.given()
                .body(jsonCreationBody)
                .header("Content-Type", "application/json")
                .when()
                .port(port)
                .request("POST", "/v1/bank/user");
    }

    private String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}