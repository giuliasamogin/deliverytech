package com.deliverytech.delivery_api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CategoriaValidator implements ConstraintValidator<ValidCategoria, String> {

    // ⚠️ Lista provisória, baseada nos exemplos dos slides — confirmar com o professor
    private static final List<String> CATEGORIAS_VALIDAS = List.of(
        "ITALIANA", "JAPONESA", "BRASILEIRA", "PIZZA", "MEXICANA", "CHINESA", "ARABE", "FAST_FOOD"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return CATEGORIAS_VALIDAS.contains(value.toUpperCase());
    }
}