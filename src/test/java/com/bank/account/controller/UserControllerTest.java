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
public class UserControllerTest {

    @LocalServerPort
    private int port;
    private RequestSpecification httpRequest;

    @Before
    public void init() throws IOException {
        //Given
        httpRequest = RestAssured.given();
    }

    @Test
    public void userShouldCreateAccount() throws IOException {
        //Given
        String jsonCreationBody = generateStringFromResource("src\\test\\java\\com\\bank\\account\\payload\\user-creation-payload.json");

        //When
        Response response = httpRequest.given()
                .body(jsonCreationBody)
                .header("Content-Type", "application/json")
                .when()
                .port(port)
                .request("POST", "/v1/bank/user");

        //Then
        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertNotNull(jsonPathEvaluator.get("id"));
    }

    private String generateStringFromResource(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}