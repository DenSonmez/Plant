package dk.exam.controller.Del2.impl;

import dk.exam.config.HibernateConfig;
import dk.exam.controller.Del2.IController;
import dk.exam.dao.Del2.impl.PlantDAO;

import dk.exam.dto.Del2.PlantDTO;
import dk.exam.dto.Del2.ResellerDTO;
import dk.exam.exception.ApiException;
import dk.exam.model.Plant;
import dk.exam.model.Reseller;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
//. Den fungerer som en mellemmand mellem dine tjenester og databasen for at forenkle datatilgangen og manipulationen med Plant-objekter.
public class PlantController implements IController<Plant, Integer> {
    private final PlantDAO dao;

    public PlantController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = PlantDAO.getInstance(emf);
    }

    @Override
    public void read(Context ctx) throws ApiException {
        // Få ID fra URL-parametret og brug det til at finde den rigtige plante
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        // Kald din dao's read-metode med ID'et
        Plant plant = dao.read(id);

        if (plant == null) {
            throw new ApiException(404, "Plant not found");
        }
        ctx.res().setStatus(200);
        // Konverter planten til en PlantDTO og send den tilbage til klienten
        PlantDTO plantDTO = new PlantDTO(plant);
        ctx.json(plantDTO, PlantDTO.class);
    }


    @Override
    public void readAll(Context ctx) {
        List<Plant> plants = dao.readAll();
        List<PlantDTO> plantDTOs = PlantDTO.convertList(plants);
        ctx.json(plantDTOs);
    }


   @Override
    public void create(Context ctx) throws ApiException {
        PlantDTO plantDTO = ctx.bodyAsClass(PlantDTO.class);
//konverterer plantDTO til en Plant og opretter en ny Reseller med resellerId som vi
        Plant newPlant = new Plant(plantDTO);
        newPlant.addReseller(new Reseller(plantDTO.getResellerId()));
        newPlant = dao.create(newPlant);

        PlantDTO createdPlantDTO = new PlantDTO(newPlant);
        ctx.res().setStatus(201);
        ctx.json(createdPlantDTO, PlantDTO.class);
    }


    @Override
    public void update(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Plant plant = ctx.bodyAsClass(Plant.class);
        // Kald din dao's update-metode med ID og opdateret plante
        Plant update = dao.update(id, plant);
        // Konverter den opdaterede plante til en PlantDTO og send den tilbage til klienten
        PlantDTO convertPlant = new PlantDTO(update);
        ctx.json(convertPlant, PlantDTO.class);

    }


    @Override
    public void delete(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Plant plant = dao.delete(id);
        PlantDTO plantDTO = new PlantDTO(plant);
        ctx.res().setStatus(204);
        ctx.json(plantDTO, PlantDTO.class);
    }


    public void getPlantsByType(Context ctx) {
        String plantType = ctx.pathParam("type");
        List<Plant> plantsByType = dao.getByType(plantType);
        List<PlantDTO> plantDTOs = PlantDTO.convertList(plantsByType);
        ctx.json(plantDTOs);
    }


    public void addPlantToReseller(Context ctx) throws ApiException {
        int resellerId = ctx.pathParamAsClass("resellerId", Integer.class).get();
        int plantId = ctx.pathParamAsClass("plantId", Integer.class).get();

        Plant plant = dao.read(plantId);
        if (plant != null) {
            Reseller reseller = dao.addPlantToReseller(resellerId, plant);
            ResellerDTO resellerDTO = new ResellerDTO(reseller);
            ctx.json(resellerDTO);
        } else {
            // Håndter fejl, hvis planten ikke blev fundet
            ctx.status(404);
            ctx.result("Plant not found");
        }
    }



    public void getPlantsByReseller(Context ctx) {
        int resellerId = ctx.pathParamAsClass("resellerId", Integer.class).get();
        List<Plant> plantsByReseller = dao.getPlantsByReseller(resellerId);
        List<PlantDTO> plantDTOs = PlantDTO.convertList(plantsByReseller);

        ctx.json(plantDTOs);
    }






      /*  @Override
    public void update(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        Plant updatedPlant = ctx.bodyAsClass(Plant.class); // Få de opdaterede oplysninger fra anmodelsens krop
        Plant updatedEntity = dao.update(id, updatedPlant, Plant.class); // Kald din dao's update-metode med ID og opdateret plante

        if (updatedEntity != null) {
            PlantDTO updatedPlantDTO = new PlantDTO(updatedEntity);
            ctx.res().setStatus(200); // OK statuskode
            ctx.json(updatedPlantDTO, PlantDTO.class);
        } else {
            ctx.status(404); // Not Found statuskode, hvis opdatering mislykkedes
        }
    }*/

}











