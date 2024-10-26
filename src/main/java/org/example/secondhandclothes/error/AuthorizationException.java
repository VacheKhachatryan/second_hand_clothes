package org.example.secondhandclothes.error;

public class AuthorizationException extends BaseException {

    public AuthorizationException(ApplicationError error, String message) {
        super(error, message);
    }
}
