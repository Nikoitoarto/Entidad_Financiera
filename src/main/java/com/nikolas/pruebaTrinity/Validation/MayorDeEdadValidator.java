package com.nikolas.pruebaTrinity.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MayorDeEdadValidator implements ConstraintValidator<MayorDeEdad, LocalDate> {


    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (fechaNacimiento == null){
            return false;

        }

        return Period.between(fechaNacimiento, LocalDate.now()).getYears() >= 18;

    }

}

