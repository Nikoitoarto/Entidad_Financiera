package com.nikolas.pruebaTrinity.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
@Table(name = "cliente")
public class Cliente extends ABaseEntity {

    @Column(name = "nombres", nullable = true)
    private String nombres;

    @Column(name = "apellidos", nullable = true)
    private String apellidos;

    @Column(name = "tipo_identificacion", nullable = true)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = true)
    private String numeroIdentificacion;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "fecha_nacimiento", nullable = true)
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();

}
