package dk.exam.dao.Del2.impl;


import dk.exam.dto.Del2.PlantDTO;
import dk.exam.exception.ApiException;
import dk.exam.model.Plant;
import dk.exam.model.Reseller;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;import java.util.Map;

public class PlantDAO extends GenericDAO<Plant,Integer> {

    //static fordi vi kun skal have en instans af vores DAO
    private static PlantDAO dao;


    public PlantDAO(Class<Plant> entityClass,EntityManagerFactory  emf) {
        super(entityClass, emf);
    }
    // her bruger vi en singleton til at sikre at vi kun har en instans af vores DAO
    public static PlantDAO getInstance(EntityManagerFactory emf) {
        if (dao == null) {
            dao = new PlantDAO(Plant.class, emf);
        }
        return dao;
    }

    public Reseller addPlantToReseller(int resellerId, Plant plant) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Reseller reseller = em.find(Reseller.class, resellerId);

            if (reseller != null && plant != null) {
                reseller.addPlant(plant);
                em.persist(plant);
            }
            em.getTransaction().commit();
            return reseller;
        }
    }



    public List<Plant> getPlantsByReseller(int resellerId) {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT p FROM Plant p WHERE p.id = :resellerId", Plant.class);
            query.setParameter("resellerId", resellerId);
            return query.getResultList();
        }
    }

    public List<Plant> getByType(String type) {
           try (var em = emf.createEntityManager()) {
                var query = em.createQuery("SELECT p FROM Plant p WHERE p.plantType = :type", Plant.class);
                query.setParameter("type", type);
                List<Plant> plants = query.getResultList();
                List<PlantDTO> plantDTOs = new ArrayList<>();
                for (Plant plant : plants) {
                    plantDTOs.add(new PlantDTO(plant));
                }
                return plants;
            }
    }


    }



