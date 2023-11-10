package dk.exam.dao.Del2.impl;
import dk.exam.dao.Del2.IDAO;
import dk.exam.exception.ApiException;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;


public abstract class GenericDAO<T, S> implements IDAO<T, S> {
    private final Class<T> entityClass;
    protected final EntityManagerFactory emf;

    // vi bruger en constructor til at initialisere vores entityClass og emf for at kunne bruge dem i vores metoder
    public GenericDAO(Class<T> entityClass, EntityManagerFactory emf) {
        this.entityClass = entityClass;
        this.emf = emf;
    }


    @Override
    public T read(S key) throws ApiException {
        try (var em = emf.createEntityManager()) {
            T entity = em.find(entityClass, key);
            if (entity != null) {
                return entity;
            } else {
                throw new ApiException(404, "The requested resource was not found");
            }
        }
    }

    @Override
    public List<T> readAll() {
        try (var em = emf.createEntityManager()) {
            var query = em.createQuery("SELECT t FROM " + entityClass.getSimpleName() + " t", entityClass);
            return query.getResultList();
        }
    }

    @Override
    public T create(T entity) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new ApiException(400, "Invalid request. The resource could not be created. " + e.getMessage());
        }
    }



    @Override
    public T update(S key, T entity) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T existingEntity = em.find(entityClass, key);
            if (existingEntity != null) {
                existingEntity = em.merge(entity);
            } else {
                throw new ApiException(400, "Invalid request. The resource could not be updated.");

            }
            em.getTransaction().commit();
            return existingEntity;
        }
    }


    @Override
    public T delete(S key) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T entity = em.find(entityClass, key);
            if (entity != null) {
                em.remove(entity);
            } else {
                throw new ApiException(404, "The resource with ID " + key + " was not found");
            }
            em.getTransaction().commit();
            return entity;
        }

    }
















      /*  @Override
    public T update(Integer id, T entity, Class<T> entityType) throws ApiException {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            T managedEntity = em.find(entityType, id);

            if (managedEntity != null) {
                if (entity instanceof Plant plant && managedEntity instanceof Plant managedPlant) {
                    managedPlant.setPlantType(plant.getPlantType());
                    managedPlant.setName(plant.getName());
                    managedPlant.setMaxHeight(plant.getMaxHeight());
                    managedPlant.setPrice(plant.getPrice());
                }
                T mergedEntity = em.merge(managedEntity);
                em.getTransaction().commit();
                return mergedEntity;
            }
            return null;
        }
    }*/


}

