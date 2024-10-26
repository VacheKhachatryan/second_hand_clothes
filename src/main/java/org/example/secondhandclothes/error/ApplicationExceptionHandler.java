package org.example.secondhandclothes.error;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.secondhandclothes.error.ApplicationError.CONSTRAINT_VIOLATION;
import static org.example.secondhandclothes.error.ApplicationError.INTERNAL_ERROR;
import static org.slf4j.event.Level.ERROR;
import static org.slf4j.event.Level.INFO;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String BODY = "body";
  private static final String DEFAULT_FIELD_ERROR_MESSAGE = "The field value is invalid.";
  private static final String DEFAULT_OBJECT_ERROR_MESSAGE = "The dto object is invalid.";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ApplicationError error = CONSTRAINT_VIOLATION;
    BinaryOperator<List<String>> mergeFunction =
        (first, second) -> Stream.concat(first.stream(), second.stream()).toList();

    return new ResponseEntity<>(
        ApiError.builder()
            .errorCode(error.getCode())
            .message(error.getMessage())
            .errors(Stream.concat(ex.getBindingResult().getGlobalErrors().stream()
                        .collect(Collectors.toMap(e -> BODY, err -> getMessageOrDefault(err.getDefaultMessage(), DEFAULT_FIELD_ERROR_MESSAGE), mergeFunction))
                        .entrySet()
                        .stream(),
                        ex.getBindingResult().getFieldErrors().stream()
                        .collect(Collectors.toMap(FieldError::getField, err -> getMessageOrDefault(err.getDefaultMessage(), DEFAULT_OBJECT_ERROR_MESSAGE), mergeFunction))
                        .entrySet()
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, mergeFunction)))
            .build(),
        error.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ApplicationError error = CONSTRAINT_VIOLATION;
    return new ResponseEntity<>(
        ApiError.builder()
            .message(error.getMessage())
            .errorCode(error.getCode())
            .errors(Map.of(ex.getParameterName(), List.of(ex.getMessage())))
            .build(),
        error.getHttpStatus());
  }

  @Override
  protected ResponseEntity<Object> handleTypeMismatch(
      TypeMismatchException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ApplicationError error = CONSTRAINT_VIOLATION;
    return new ResponseEntity<>(
        ApiError.builder().errorCode(error.getCode()).message(ex.getMessage()).build(),
        error.getHttpStatus());
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
    ApplicationError error = CONSTRAINT_VIOLATION;

    return new ResponseEntity<>(
        ApiError.builder()
            .errorCode(error.getCode())
            .message(error.getMessage())
            .errors(ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                    err -> err.getPropertyPath().toString(), err -> List.of(err.getMessage()))))
            .build(),
        error.getHttpStatus());
  }

  @ExceptionHandler({BaseException.class})
  public ResponseEntity<ApiError> handleBaseException(BaseException ex) {
    ApplicationError error = ex.getError();
    log.atLevel(error == INTERNAL_ERROR ? ERROR : Optional.ofNullable(ex.getLevel()).orElse(INFO))
        .log(ex.getMessage());

    return new ResponseEntity<>(
        ApiError.builder().errorCode(error.getCode()).message(error.getMessage()).build(),
        error.getHttpStatus());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ApiError> handleException(Exception ex) {
    ApplicationError error = INTERNAL_ERROR;
    log.error(ex.getMessage());

    return new ResponseEntity<>(
        ApiError.builder().errorCode(error.getCode()).message(error.getMessage()).build(),
        error.getHttpStatus());
  }

  private List<String> getMessageOrDefault(String message, String defaultMessage) {
    return Optional.ofNullable(message).map(List::of).orElse(List.of(defaultMessage));
  }
}
