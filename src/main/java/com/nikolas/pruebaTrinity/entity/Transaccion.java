package com.nikolas.pruebaTrinity.entity;


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

    @ManyToOne
    @JoinColumn(name = "producto", nullable = true)
    private Producto producto;
}
