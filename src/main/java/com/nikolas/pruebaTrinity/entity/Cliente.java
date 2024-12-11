package com.nikolas.pruebaTrinity.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikolas.pruebaTrinity.Validation.MayorDeEdad;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "cliente")
public class Cliente extends ABaseEntity {

    @Size(min = 2, message = "El nombre debe de tener al menos 2 caracteres")
    @Column(name = "nombres", nullable = true)
    private String nombres;

    @Size(min = 2, message = "El apellido debe de tener al menos 2 caracteres")
    @Column(name = "apellidos", nullable = true)
    private String apellidos;

    @Column(name = "tipo_identificacion", nullable = true)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = true)
    private String numeroIdentificacion;

    @Email(message = "El correo debe de tener un formato valido (ejemplo: xxxx@xxxxx.xxx)")
    @Column(name = "email", nullable = true)
    private String email;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    @MayorDeEdad
    @Column(name = "fecha_nacimiento", nullable = true)
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();


}
