package dk.exam.dao.Del2;


import dk.exam.exception.ApiException;
import dk.exam.model.Plant;

import java.util.List;

public interface IDAO<T, D> {

    T read(D d) throws ApiException;
    List<T> readAll();
    T create(T t) throws ApiException;
    T update(D d, T t) throws ApiException;
 //   T update(Integer id, T entity, Class<T> entityType) throws ApiException;


    T delete(D d) throws ApiException;
   // List<PlantDTO> getByType(String type);



}
