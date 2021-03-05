package by.prohor.service.common;

import java.util.List;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

public interface TransportParkService<T> {

    List<T> getAll();

    T save(T model);

    Integer delete(Integer id);

    Integer update(T model);

}

