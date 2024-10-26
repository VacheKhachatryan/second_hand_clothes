package org.example.secondhandclothes.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {

  public static final String USER_ALREADY_EXISTS_MESSAGE =
      "There is user already created with such email: %s";
  public static final String USER_NOT_FOUND_IN_AUTHENTICATION_MESSAGE =
      "There is no user found with the email provided during login.";
  public static final String INCORRECT_PASSWORD_MESSAGE =
      "The password provided along with the email: %s is incorrect.";
  public static final String REFRESH_TOKEN_NOT_FOUND_MESSAGE =
      "The refresh token provided: %s is not found.";
  public static final String INVALID_REFRESH_TOKEN_MESSAGE =
      "The refresh token provided: %s is invalid.";
  public static final String GARMENT_CATEGORY_EXISTS_MESSAGE =
      "There is garment category already created with such name: %s";
  public static final String GARMENT_CATEGORY_NOT_FOUND_MESSAGE =
      "There is no garment category found with such id: %s";
  public static final String GARMENT_NOT_FOUND_MESSAGE =
      "There is no garment found with such id: %s";
  public static final String PUBLISHER_NOT_FOUND_MESSAGE =
      "There is no publisher found with such id: %s";
  public static final String ACCESS_DENIED_MESSAGE =
      "User with email: %s don't have enough permissions to access this resource.";
  public static final String JSON_SERIALIZATION_FAILURE_MESSAGE =
      "Couldn't serialize the requested object due to an unexpected error.";
}
