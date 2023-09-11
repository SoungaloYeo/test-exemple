package com.testexample.controller.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumNamePatternValidator implements ConstraintValidator<EnumNamePattern, Enum<?>> {
    private Pattern pattern;

    @Override
    public void initialize(EnumNamePattern annotation) {

        try {
            this.pattern = Pattern.compile(annotation.regexp());
        } catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException("Regex not valid", ex);
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return false;
        } else {
            Matcher m = this.pattern.matcher(value.name());
            return m.matches();
        }
    }
}
