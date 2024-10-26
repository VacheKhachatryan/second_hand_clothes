package org.example.secondhandclothes.error;

public class EntityAlreadyExistsException extends BaseException {

    public EntityAlreadyExistsException(ApplicationError error, String message) {
        super(error, message);
    }
}
