package dk.exam.controller.Del1;

import dk.exam.controller.Del2.IController;
import dk.exam.exception.ApiException;
import io.javalin.http.Context;

public interface IMockController extends IController {
    void getPlantsByType(Context ctx) throws ApiException;
}
