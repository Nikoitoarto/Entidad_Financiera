package com.nikolas.pruebaTrinity.Dto;


import com.nikolas.pruebaTrinity.entity.Transaccion;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransaccionDto {
    private Transaccion Transaccion;
    private Long productoId;
    private Long productoDestinoId;


}
