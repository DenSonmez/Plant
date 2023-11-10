package dk.exam.controller.Del1;

import dk.exam.dao.del1.MockDAO;
import dk.exam.dto.Del1.MockDTO;
import dk.exam.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public class MockDAOController implements IMockController {

    @Override
    public void read(Context ctx) throws ApiException {
        //  int id = Integer.parseInt(ctx.pathParam("id"));
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        MockDTO plant = MockDAO.getById(id);
        ctx.json(plant);

    }

    @Override
    public void readAll(Context ctx) {
        ctx.json(MockDAO.getAll());


    }

    @Override
    public void create(Context ctx) {
        MockDTO newPlant = ctx.bodyAsClass(MockDTO.class);
        MockDTO createdPlant = MockDAO.create(newPlant);
        ctx.status(201).json(createdPlant);

    }

    @Override
    public void update(Context ctx) throws ApiException {
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        MockDTO updatedPlant = ctx.bodyAsClass(MockDTO.class);
        MockDTO plant = MockDAO.update(id, updatedPlant);
        ctx.json(plant);

    }

    @Override
    public void delete(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        MockDAO.delete(id);
        ctx.status(204);

    }


    @Override
    public void getPlantsByType(Context ctx) throws ApiException {
        String type = ctx.pathParam("type");
        List<MockDTO> plants = MockDAO.getByType(type);
        if (plants.isEmpty()) {
            throw new ApiException(404, "No plants of type " + type + " found");
            //ctx.status(404).result("No plants of type " + type + " found");
        } else {
            ctx.json(plants);
        }
    }


    public void getByMaxHeight(Context ctx) throws ApiException {
        int maxHeight = Integer.parseInt(ctx.pathParam("maxHeight"));
        List<MockDTO> plants = MockDAO.getByMaxHeight(maxHeight);
        if (plants.isEmpty()) {
            throw new ApiException(404, "No plants with a max height of " + maxHeight + " found");
           // ctx.status(404).result("No plants with a max height of " + maxHeight + " found");
        } else {
            ctx.json(plants);
        }
    }


    public void getPlantNames(Context ctx) throws ApiException {
        List<String> plants = MockDAO.getPlantNames();
        if (plants.isEmpty()) {
            ctx.status(404).result("No plants found");
        } else {
            ctx.json(plants);
        }
    }


    public void sortByName(Context ctx) throws ApiException {
        List<MockDTO> plants = MockDAO.sortByName();
        if (plants.isEmpty()) {
            ctx.status(404).result("No plants found");
        } else {
            ctx.json(plants);
        }

    }

}
