package com.services.rest_service;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.config.SSLConfig;
import io.restassured.specification.RequestSpecification;

public class ApigeeRestClient extends RestAssured {
    private static PreemptiveBasicAuthScheme authScheme=new PreemptiveBasicAuthScheme();

    public static RequestSpecification apigeeClient(){
        RestAssured.useRelaxedHTTPSValidation();
        given().config(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
        return given();
    }
}
