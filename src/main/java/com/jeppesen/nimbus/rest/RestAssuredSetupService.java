package com.jeppesen.nimbus.rest;


import com.jayway.restassured.RestAssured;

public final class RestAssuredSetupService {

    private RestAssuredSetupService() {
    }

    public static RestAssuredSetupService create() {
        return new RestAssuredSetupService();
    }

    public RestAssuredSetupService setBasicAuthCredentials(String username, String password) {
        RestAssured.authentication = RestAssured.preemptive().basic(username, password);
        return this;
    }

    public RestAssuredSetupService baseUri(String baseUri) {

        RestAssured.baseURI = baseUri;

        return this;
    }
}
