package com.smarttek.chatgptbot.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstField;
    private String secondField;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstField = constraintAnnotation.first();
        secondField = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object firstFieldValue = new BeanWrapperImpl(o)
                .getPropertyValue(firstField);
        Object secondFieldValue = new BeanWrapperImpl(o)
                .getPropertyValue(secondField);

        if (firstFieldValue != null && secondFieldValue != null) {
            return firstFieldValue.equals(secondFieldValue);
        } else {
            return false;
        }
    }
}
