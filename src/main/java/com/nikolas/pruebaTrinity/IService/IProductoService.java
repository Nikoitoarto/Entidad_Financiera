package com.nikolas.pruebaTrinity.IService;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.ProductoDto;
import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.entity.Producto;

public interface IProductoService extends IBaseService<Producto> {
    ApiResponseDto<Producto> crearProducto(ProductoDto productoDto) throws Exception;
    ApiResponseDto<Producto> actualizarEstadoProducto(Long productoID, EstadoProducto nuevoEstado) throws Exception;

}
