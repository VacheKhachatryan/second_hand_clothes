package org.example.secondhandclothes.error;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(ApplicationError error, String message) {
        super(error, message);
    }
}
