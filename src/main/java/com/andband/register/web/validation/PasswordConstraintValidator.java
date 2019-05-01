package com.andband.register.web.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(Password constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean isValidPassword = true;

        if (StringUtils.isEmpty(password) && password.length() < 4) {
            isValidPassword = false;
        }

        return isValidPassword;
    }

}
