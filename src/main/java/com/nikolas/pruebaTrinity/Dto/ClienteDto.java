package com.nikolas.pruebaTrinity.Dto;

import com.nikolas.pruebaTrinity.Validation.MayorDeEdad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteDto {

    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombres;

    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellidos;

    private String tipoIdentificacion;

    private String numeroIdentificacion;

    @Email(message = "El correo debe tener un formato válido (ejemplo: xxxx@xxxxx.xxx)")
    private String email;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @MayorDeEdad
    private LocalDate fechaNacimiento;
}
