package by.prohor.dao.common;

/**
 * Created by Artsiom Prokharau 22.02.2021
 */


public interface TransportParkDao<T> {

    T save(T model);

    Integer delete(Integer id);

    Integer update(T model);

    T findById(Integer id);

}
