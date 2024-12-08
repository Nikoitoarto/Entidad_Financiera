package com.nikolas.pruebaTrinity.Dto;


import com.nikolas.pruebaTrinity.entity.Cliente;
import com.nikolas.pruebaTrinity.entity.Producto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDto {
    private Producto producto;
    private Long clienteId;
    private Cliente cliente;
}
