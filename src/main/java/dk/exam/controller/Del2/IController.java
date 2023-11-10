package dk.exam.controller.Del2;

import dk.exam.dto.Del2.PlantDTO;
import dk.exam.exception.ApiException;
import io.javalin.http.Context;

import java.util.List;

public interface IController<T, D> {
    void read(Context ctx) throws ApiException;
    void readAll(Context ctx);
    void create(Context ctx) throws ApiException;
    void update(Context ctx) throws ApiException;

    void delete(Context ctx) throws ApiException;


    //List<PlantDTO> getByType(String type);
}
