package com.services.rest_service;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;

public class RestAssuredClient extends RestAssured {
    public static RequestSpecification httpClient(){
        RestAssured.useRelaxedHTTPSValidation();
        given().config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
    return given();
    }
    public static RequestSpecification soapClient(){
        RestAssured.useRelaxedHTTPSValidation();
        given().config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        return given();
    }
}
