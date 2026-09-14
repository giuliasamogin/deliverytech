package com.deliverytech.delivery_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CategoriaValidator implements ConstraintValidator<ValidCategoria, String> {

    private static final List<String> CATEGORIAS_VALIDAS = List.of(
        "Italiana", "Brasileira", "Japonesa", "Mexicana", "Árabe"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return CATEGORIAS_VALIDAS.contains(value);
    }
}