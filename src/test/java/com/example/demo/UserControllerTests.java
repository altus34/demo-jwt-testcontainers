package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.jwt.JwtContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerTests {
    @LocalServerPort
    private int port;

    @Container
    private static final JwtContainer oktaServer = new JwtContainer();

    @DynamicPropertySource
    private static void config(DynamicPropertyRegistry registry) {
        registry.add("ISSUER_URI", oktaServer::issuer);
        registry.add("ISSUER_PUBLIC_KEY", oktaServer::issuer);
    }

    @Test
    void unsecuredJwtTest() {
        // @formatter:off
        given()
            .port(port)
        .when()
            .get("/users/me")
        .then()
            .statusCode(200)
            .assertThat()
            .body("key-1", is("value-1"));
        // @formatter:on
    }

    @Test
    void securedJwtTest() {
        String jwtToken = oktaServer.forgery()
                .withJWTId("jwtId")
                .withArrayClaims("scp", "ADMIN")
                .withAudience("Audience")
                .forge();

        // @formatter:off
        given()
            .port(port)
            .auth().oauth2(jwtToken)
        .when()
            .get("/users/me2")
        .then()
            .statusCode(200)
            .assertThat()
            .body("key-1", is("value-1"));
        // @formatter:on
    }

}