package com.nikolas.pruebaTrinity.entity;


import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.Enum.TipoProducto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "producto")
public class Producto extends ABaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_producto", nullable = true)
    private TipoProducto tipoProducto;

    @Column(name = "numero_ cuenta", unique = true, nullable = true)
    private double numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadoProducto", nullable = true)
    private EstadoProducto estadoProducto;

    @Column(name = "saldo", nullable = true)
    private double saldo;

    @Column(name = "exenta_gmf", nullable = true)
    private Boolean exentaGmf;

    @Column(name = "nombre_cliente", nullable = true)
    private String nombreCliente;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Transaccion> transacciones = new HashSet<>();

}
