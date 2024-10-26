package org.example.secondhandclothes.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.secondhandclothes.validator.PaginationEnableValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PaginationEnableValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
public @interface PaginationEnable {

  String message() default "The sum of offset and limit should be divisible by the limit.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
