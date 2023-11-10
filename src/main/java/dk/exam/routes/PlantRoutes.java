package dk.exam.routes;

import dk.exam.controller.Del2.impl.PlantController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class PlantRoutes {
    private static final PlantController CONTROLLER = new PlantController();



    protected EndpointGroup getRoutes() {

        return () -> {
            path("/plants", () -> {
                get("/", CONTROLLER::readAll);
                get("/type/{type}", CONTROLLER::getPlantsByType);
                get("/{id}", CONTROLLER::read);
                post("/", CONTROLLER::create);
                delete("/{id}", CONTROLLER::delete);
                put("/{id}", CONTROLLER::update);
                post("/reseller/{resellerId}/{plantId}", CONTROLLER::addPlantToReseller);
                get("/reseller/{resellerId}", CONTROLLER::getPlantsByReseller);


            });
        };
    }
}
