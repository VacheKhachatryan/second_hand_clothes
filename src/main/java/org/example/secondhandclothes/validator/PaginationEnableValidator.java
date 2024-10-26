package org.example.secondhandclothes.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.example.secondhandclothes.dto.request.GarmentFilterDto;
import org.example.secondhandclothes.validator.constraint.PaginationEnable;
import org.springframework.stereotype.Component;

@Component
public class PaginationEnableValidator
    implements ConstraintValidator<PaginationEnable, GarmentFilterDto> {

  @Override
  public boolean isValid(
      GarmentFilterDto value, ConstraintValidatorContext constraintValidatorContext) {
    int offset = value.getOffset();
    int limit = value.getLimit();
    if ((offset + limit) % limit != 0) {
      return false;
    }

    return true;
  }
}
