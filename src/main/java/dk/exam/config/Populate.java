package dk.exam.config;


import dk.exam.model.Plant;
import dk.exam.model.Reseller;
import jakarta.persistence.EntityManagerFactory;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class Populate {
 public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            List<Plant> plants = Arrays.asList(
                    new Plant("Rose", "Albertine", 400, 199.5f),
                    new Plant("Bush", "Aronia", 200, 169.5f),
                    new Plant("FruitAndBerries", "AromaApple", 350, 399.5f),
                    new Plant("Rhododendron", "Astrid", 40, 269.5f),
                    new Plant("Rose", "TheDarkLady", 100, 199.5f),
                    new Plant( "FruitAndBerries", "Apple", 350, 399.5f)
            );


            List<Reseller> resellerList = Arrays.asList(
                    new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334"),
                    new Reseller("Glostrup Planter", "Tværvej 35", "32233232"),
                    new Reseller("Holbæk Planteskole", "Stenhusvej 49", "59430945"),
                    new Reseller( "Københavns Planteskole", "Hovedvejen 1", "33212334")
            );

            // Opret forhold mellem Patient og Appointment
            resellerList.get(0).setPlants(new HashSet<>(plants.subList(0, 2)));
            resellerList.get(1).setPlants(new HashSet<>(plants.subList(2, 3)));
            resellerList.get(2).setPlants(new HashSet<>(plants.subList(3, 4)));
            resellerList.get(3).setPlants(new HashSet<>(plants.subList(4, 5)));


            // Persistér Appointment-objekter i databasen
            resellerList.forEach(em::persist);

            em.getTransaction().commit();
        }
    }
}


   /* public static void main(String[] args) {

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        Set<Plant> plants = getPlants();

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Reseller lyngby = new Reseller("Lyngby Plantecenter", "Firskovvej 18", "33212334");
            Reseller glostrup = new Reseller("Glostrup Planter", "Tværvej 35", "32233232");
            Reseller holbæk = new Reseller("Holbæk Planteskole", "Stenhusvej 49", "59430945");

            lyngby.setPlants(plants);
            glostrup.setPlants(plants);
            holbæk.setPlants(plants);

            em.persist(lyngby);
            em.persist(glostrup);
            em.persist(holbæk);

            em.getTransaction().commit();
        }
    }

    @NotNull
    private static Set<Plant> getPlants() {
        Set<Plant> plants = new HashSet<>();

        plants.add(new Plant("Rose", "Albertine", 400, 199.5f));
        plants.add(new Plant("Bush", "Aronia", 200, 169.5f));
        plants.add(new Plant("FruitAndBerries", "AromaApple", 350, 399.5f));
        plants.add(new Plant("Rhododendron", "Astrid", 40, 269.5f));
        plants.add(new Plant("Rose", "TheDarkLady", 100, 199.5f));
        plants.add(new Plant("FruitAndBerries", "Apple", 350, 399.5f));

     }    return plants;
*/






