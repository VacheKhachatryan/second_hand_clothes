package org.example.secondhandclothes.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.secondhandclothes.validator.PasswordValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface Password {

  String message() default "Invalid password.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
