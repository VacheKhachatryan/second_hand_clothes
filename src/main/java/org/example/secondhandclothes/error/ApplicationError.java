package org.example.secondhandclothes.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationError {

  // 400, Bad Request
  CONSTRAINT_VIOLATION(4001, BAD_REQUEST, "There is an invalid value in the user input."),

  // 401, Unauthorized
  INVALID_USER_CREDENTIALS(4011, UNAUTHORIZED, "Invalid email or password."),
  INVALID_REFRESH_TOKEN(4012, UNAUTHORIZED, "Invalid or expired refresh token."),
  AUTH_HEADER_IS_MISSING(4013, UNAUTHORIZED, "Missing authorization header."),
  BEARER_IS_MISSING(4014, UNAUTHORIZED, "Missing bearer prefix in the token."),
  ACCESS_TOKEN_IS_EXPIRED(4015, UNAUTHORIZED, "Access token is expired."),
  INVALID_ACCESS_TOKEN(4016, UNAUTHORIZED, "Invalid access token."),

  // 403, Forbidden
  ACCESS_DENIED(4031, FORBIDDEN, "User don't have enough permissions to access this resource."),

  // 404, Not Found
  GARMENT_CATEGORY_NOT_FOUND(4041, NOT_FOUND, "There is no garment category found with such id."),
  USER_NOT_FOUND(4042, NOT_FOUND, "There is no user found with such id."),
  GARMENT_NOT_FOUND(4043, NOT_FOUND, "There is no garment found with such id."),

  // 409, Conflict
  USER_ALREADY_EXISTS_WITH_EMAIL(4091, CONFLICT, "There is a user registered with such email."),
  GARMENT_CATEGORY_ALREADY_EXISTS_WITH_NAME(
      4092, CONFLICT, "There is a garment category registered with such name."),
  GARMENT_CATEGORY_IS_IN_USE(
      4093,
      CONFLICT,
      "There are garments created under this category. Consider deleting or modifying them first."),

  // 500, Internal
  INTERNAL_ERROR(5001, INTERNAL_SERVER_ERROR, "Something went wrong, please try later.");

  private final Integer code;
  private final HttpStatus httpStatus;
  private final String message;
}
