package org.example.secondhandclothes.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.secondhandclothes.validator.constraint.NullOrNotBlank;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, String> {

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return Objects.isNull(s) || !s.isBlank();
  }
}
