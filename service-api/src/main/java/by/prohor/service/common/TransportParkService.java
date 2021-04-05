package by.prohor.service.common;

/**
 * Created by Artsiom Prokharau 04.03.2021
 */

public interface TransportParkService<T> {

    T save(T model);

    Integer delete(Integer id);

    Integer update(T model);

    T findById(Integer id);
}

