package dk.exam.controller;


import dk.exam.config.ApplicationConfig;
import dk.exam.config.HibernateConfig;
import dk.exam.controller.Del2.impl.PlantController;

import dk.exam.dto.Del2.PlantDTO;
import dk.exam.model.Plant;
import dk.exam.model.Reseller;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PlantControllerTest {


    private static Javalin app;
    private static final String BASE_URL = "http://localhost:7777/api/v1";
    private static EntityManagerFactory emfTest;
    Plant p1, p2, p3, p4, p5;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTest(true);
        emfTest = HibernateConfig.getEntityManagerFactory();
        PlantController plantController = new PlantController();
        app = Javalin.create();
        ApplicationConfig.startServer(app, 7777);
    }

    @BeforeEach
    void setUp() {
        Reseller r1 = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
        Reseller r2 = new Reseller("Glostrup Planter", "Tværvej 35", "32233232");
        Reseller r3 = new Reseller("Holbæk Planteskole", "Stenhusvej 49", "59430945");
        try (var em = emfTest.createEntityManager()) {
            em.getTransaction().begin();
            // Delete all rows
            em.createQuery("DELETE FROM Plant p").executeUpdate();
            em.createQuery("DELETE FROM Reseller r").executeUpdate();
            // Reset sequence
            em.createNativeQuery("ALTER SEQUENCE plant_plant_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE reseller_reseller_id_seq RESTART WITH 1").executeUpdate();

            em.persist(r1);
            em.persist(r2);
            em.persist(r3);

            p1 = new Plant("Rose", "Albertine", 400, 199.5f);
            p2 = new Plant("Bush", "Aronia", 200, 169.5f);
            p3 = new Plant("FruitAndBerries", "AromaApple", 350, 399.5f);
            p4 = new Plant("Rhododendron", "Astrid", 40, 269.5f);
            p5 = new Plant("Rose", "TheDarkLady", 100, 199.5f);

            p1.addReseller(r1);
            p2.addReseller(r2);
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        }
    }


    @AfterAll
    static void tearDown() {
        HibernateConfig.setTest(false);
        ApplicationConfig.stopServer(app);
    }


    @Test
    void read() {
        given()
                .contentType("application/json")
                .when() // når vi sender en get request til den her url
                .get(BASE_URL + "/plants/" + p1.getId()) // det her er den url vi sender til
                .then() // det her tjekker om at
                .assertThat() // den søger for om vi får det rigtige tilbage
                .statusCode(HttpStatus.OK_200)
                .body("id", equalTo(p1.getId())); // det her tjekker om det er den samme id
    }


    @Test
    void readAll() {
        // Given -> When -> Then
        List<PlantDTO> plantDtoList =
                given()
                        .contentType("application/json")
                        .when()
                        .get(BASE_URL + "/plants")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK_200)
                        .extract().body().jsonPath().getList("", PlantDTO.class); //Dette trin ekstraherer JSON-data fra svaret og konverterer dem til en liste af HotelDTO-objekter.

        PlantDTO p1DTO = new PlantDTO(p1);
        PlantDTO p2DTO = new PlantDTO(p2);
        PlantDTO p3DTO = new PlantDTO(p3);

        assertEquals(plantDtoList.size(), 3);
        assertThat(plantDtoList, containsInAnyOrder(p1DTO, p2DTO, p3DTO));
    }

    @Test
    void create() {
        PlantDTO newPlant = new PlantDTO("Tulip", "Lulu", 50, 9.99f);
        newPlant.setResellerId(2);

        // Foretag en POST-anmodning for at oprette den
        given()
                .contentType("application/json")
                .body(newPlant)
                .when()
                .post(BASE_URL + "/plants")
                .then()
                .statusCode(201)
                .body("plantType", equalTo("Tulip"))
                .body("name", equalTo("Lulu"));

    }

    @Test
    void delete() {
        // Remove Rose1
        given()
                .contentType("application/json")
                .when()
                .delete(BASE_URL + "/plants/" + p1.getId())
                .then()
                .log().body()
                .statusCode(204);

        // Check that it is gone
        given()
                .contentType("application/json")
                .when()
                .get(BASE_URL + "/plants/" + p1.getId())
                .then()
                .statusCode(404);
    }

    @Test
    void getPlantByType() {
        // Given -> When -> Then
        List<PlantDTO> plantDtoList =
                given()
                        .contentType("application/json")
                        .when()
                        .get(BASE_URL + "/plants/type/Rose")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK_200)
                        .extract().body().jsonPath().getList("", PlantDTO.class);

        PlantDTO p1DTO = new PlantDTO(p1);
        //PlantDTO p5DTO = new PlantDTO(p5);

        assertEquals(plantDtoList.size(), 1); // der er kun 1 rose
        assertEquals(plantDtoList.get(0), p1DTO); //id 1 er rose
    }
}