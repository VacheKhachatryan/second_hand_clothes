package org.example.secondhandclothes.error;

public class OperationNotAllowedException extends BaseException {

  public OperationNotAllowedException(ApplicationError error, String message) {
    super(error, message);
  }
}
