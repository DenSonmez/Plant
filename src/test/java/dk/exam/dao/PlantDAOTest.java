package dk.exam.dao;

import dk.exam.config.HibernateConfig;
import dk.exam.dao.Del2.impl.PlantDAO;
import dk.exam.exception.ApiException;
import dk.exam.model.Plant;
import dk.exam.model.Reseller;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlantDAOTest {


        private static Javalin app;
        private static final String BASE_URL = "http://localhost:7777/api/v1";
        private static EntityManagerFactory emfTest;
        static PlantDAO plantDAO;
        private static Plant p1, p2;

        @BeforeAll
        static void beforeAll() {
            HibernateConfig.setTest(true);
            emfTest = HibernateConfig.getEntityManagerFactory();
            plantDAO = PlantDAO.getInstance(emfTest);

        }

        @BeforeEach
        void setUp() {

            try (var em = emfTest.createEntityManager()) {
                em.getTransaction().begin();
                // Delete all rows
                em.createQuery("DELETE FROM Plant p").executeUpdate();
                em.createQuery("DELETE FROM Reseller r").executeUpdate();
                // Reset sequence
                em.createNativeQuery("ALTER SEQUENCE plant_plant_id_seq RESTART WITH 1").executeUpdate();
                em.createNativeQuery("ALTER SEQUENCE reseller_reseller_id_seq RESTART WITH 1").executeUpdate();

                Reseller r1 = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
                Reseller r2 = new Reseller("Glostrup Planter", "Tværvej 35", "32233232");

                em.persist(r1);
                em.persist(r2);

                p1 = new Plant("Rose", "Albertine", 60, 199.50f);
                p2 = new Plant("Bush", "Aronia", 20, 5.99f);
                p1.setReseller(r1);
                p2.setReseller(r2);

                em.persist(p1);
                em.persist(p2);
                em.getTransaction().commit();

            }
        }

        @AfterAll
        static void tearDown() {
            HibernateConfig.setTest(false);
        }


        @Test
        void readAll() {
            List<Plant> plants = plantDAO.readAll();
            assertEquals(2, plants.size());
            assertEquals("Rose", plants.get(0).getPlantType());
            assertEquals("Bush", plants.get(1).getPlantType());
        }

        @Test
        void read() throws ApiException {
            Plant plant = plantDAO.read(p1.getId());
            assertEquals(p1, plant);
        }

        @Test
        void getByType() {
            List<Plant> plants = plantDAO.getByType("Rose");
            assertEquals(1, plants.size());
            assertEquals("Albertine", plants.get(0).getName());
        }

        @Test
        void create() throws ApiException {
            Plant plant = new Plant("Rose2", "Tulip", 60, 199.50f);
            plant.setReseller(new Reseller(2));

            Plant createdPlant = plantDAO.create(plant);
            assertEquals(3, plantDAO.readAll().size());
            assertEquals("Tulip", createdPlant.getName());
        }

        @Test
        void delete() throws ApiException {
            Plant plant = plantDAO.delete(p1.getId());
            assertEquals(1, plantDAO.readAll().size());
            assertEquals("Rose", plant.getPlantType());
        }


    }