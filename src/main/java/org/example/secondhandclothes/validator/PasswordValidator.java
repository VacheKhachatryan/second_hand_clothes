package org.example.secondhandclothes.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.secondhandclothes.validator.constraint.Password;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    if (password == null || password.isBlank()) {
      return true;
    }
    org.passay.PasswordValidator passwordValidator =
        new org.passay.PasswordValidator(
            Arrays.asList(
                new LengthRule(8, 128),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));

    RuleResult result = passwordValidator.validate(new PasswordData(password));

    if (result.isValid()) {
      return true;
    }
    constraintValidatorContext
        .buildConstraintViolationWithTemplate(
            passwordValidator.getMessages(result).stream().findFirst().orElseThrow())
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
    return false;
  }
}
