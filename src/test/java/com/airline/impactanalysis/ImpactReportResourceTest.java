package com.airline.impactanalysis;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ImpactReportResourceTest {

    @Test
    public void testGetAllImpactReports() {
        given()
            .when().get("/api/v1/impact-reports")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(notNullValue());
    }

    @Test
    public void testGetBySeverity() {
        given()
            .when().get("/api/v1/impact-reports/severity/HIGH")
            .then()
            .statusCode(200);
    }

    @Test
    public void testGetByIdNotFound() {
        given()
            .when().get("/api/v1/impact-reports/00000000-0000-0000-0000-000000000000")
            .then()
            .statusCode(404);
    }

    @Test
    public void testHealthCheck() {
        given()
            .when().get("/q/health")
            .then()
            .statusCode(200)
            .body("status", equalTo("UP"));
    }
}