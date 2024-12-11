package com.nikolas.pruebaTrinity.IService;


import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.TransaccionDto;
import com.nikolas.pruebaTrinity.entity.Transaccion;

public interface ITransaccionService extends IBaseService<Transaccion> {
    ApiResponseDto<Transaccion> crearTransaccion(TransaccionDto transaccionDto) throws Exception;
}
