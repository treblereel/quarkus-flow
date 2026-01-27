package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;

import io.quarkiverse.flow.casehub.model.ContextBlob;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is("Hello from Quarkus REST"));
    }

    @Test
    @Transactional
    public void testContextBlob() {
        ContextBlob contextBlob = new ContextBlob();
        contextBlob.name = "ZZZZ";
        contextBlob.persist();

        assertNotNull(contextBlob.id);
    }

}
