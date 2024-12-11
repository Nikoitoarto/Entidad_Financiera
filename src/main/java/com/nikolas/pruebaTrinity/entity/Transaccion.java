package com.nikolas.pruebaTrinity.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikolas.pruebaTrinity.Enum.TipoTransaccion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "transaccion")
public class Transaccion extends ABaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transaccion", nullable = true)
    private TipoTransaccion tipoTransaccion;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "producto", nullable = true)
    private Producto producto;

}
