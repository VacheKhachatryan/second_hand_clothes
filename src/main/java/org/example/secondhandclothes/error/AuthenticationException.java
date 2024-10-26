package org.example.secondhandclothes.error;

public class AuthenticationException extends BaseException {

  public AuthenticationException(ApplicationError error, String message) {
    super(error, message);
  }
}
