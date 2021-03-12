package by.prohor.dao.exception;

/**
 * Created by Artsiom Prokharau 12.03.2021
 */

public class DuplicateEntityInDbException extends RuntimeException {

    public DuplicateEntityInDbException(String message) {
        super(message);
    }

}
