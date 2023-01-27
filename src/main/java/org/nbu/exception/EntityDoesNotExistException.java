package org.nbu.exception;

public class EntityDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityDoesNotExistException(String message) {
        super(message);
    }

}
