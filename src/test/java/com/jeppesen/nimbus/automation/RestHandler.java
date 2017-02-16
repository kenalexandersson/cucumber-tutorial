package com.jeppesen.nimbus.automation;

import com.jayway.restassured.response.ValidatableResponse;
import com.jeppesen.nimbus.rest.RestAssuredSetupService;
import cucumber.api.java.Before;

import static com.jayway.restassured.RestAssured.given;

public class RestHandler {

    @Before
    public void setup() {
        RestAssuredSetupService.create()
                .baseUri("http://services.groupkt.com/country/get/iso2code");
    }

    public ValidatableResponse performGetRequest(String path, String accept) {


        return given()
                .accept(accept)
                .when()
                .log().everything()
                .get(path)
                .then()
                .log().everything()
                .statusCode(200);
    }
}
