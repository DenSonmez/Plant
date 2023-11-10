package dk.exam.routes.Del1;

import dk.exam.controller.Del1.MockDAOController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MockRoutes {

    private static final MockDAOController MOCK_CONTROLLER = new MockDAOController();

    public EndpointGroup getMockRoutes() {
        return () -> {
            path("/plants", () -> {
                get("/", MOCK_CONTROLLER::readAll);
                get("/type/{type}", MOCK_CONTROLLER::getPlantsByType);
                get("/{id}", MOCK_CONTROLLER::read);
                post("/", MOCK_CONTROLLER::create);
                delete("/{id}", MOCK_CONTROLLER::delete);
                put("/{id}", MOCK_CONTROLLER::update);
            });
        };
    }
}

