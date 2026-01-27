package io.quarkiverse.flow.casehub.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkiverse.flow.casehub.model.ContextBlob;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ContextBlobEndpointTest {

    @Inject
    EntityManager entityManager;

    @BeforeEach
    @Transactional
    void setUp() {
        ContextBlob.deleteAll();
    }

    @Test
    void testGetEmptyList() {
        given()
                .when().get("/api/customers")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    @Transactional
    void testPersistContextBlob() {
        ContextBlob blob = new ContextBlob();
        blob.name = "TestBlob";
        blob.persist();

        assertNotNull(blob.id);

        ContextBlob found = ContextBlob.findById(blob.id);
        assertNotNull(found);
        assertEquals("TestBlob", found.name);
    }

    @Test
    @Transactional
    void testFindByName() {
        ContextBlob blob = new ContextBlob();
        blob.name = "UniqueTestName";
        blob.persist();

        ContextBlob found = ContextBlob.find("name", "UniqueTestName").firstResult();
        assertNotNull(found);
        assertEquals("UniqueTestName", found.name);
    }

    @Test
    @Transactional
    void testUpdateContextBlob() {
        ContextBlob blob = new ContextBlob();
        blob.name = "Original";
        blob.persist();

        Long id = blob.id;

        ContextBlob toUpdate = ContextBlob.findById(id);
        toUpdate.name = "Updated";
        toUpdate.persist();

        ContextBlob updated = ContextBlob.findById(id);
        assertEquals("Updated", updated.name);
    }

    @Test
    @Transactional
    void testDeleteContextBlob() {
        ContextBlob blob = new ContextBlob();
        blob.name = "ToDelete";
        blob.persist();

        Long id = blob.id;
        assertNotNull(ContextBlob.findById(id));

        ContextBlob.deleteById(id);

        assertEquals(null, ContextBlob.findById(id));
    }

    @Test
    @Transactional
    void testCountContextBlobs() {
        assertEquals(0, ContextBlob.count());

        ContextBlob blob1 = new ContextBlob();
        blob1.name = "Blob1";
        blob1.persist();

        ContextBlob blob2 = new ContextBlob();
        blob2.name = "Blob2";
        blob2.persist();

        assertEquals(2, ContextBlob.count());
    }
}
