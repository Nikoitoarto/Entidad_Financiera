package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.ProductoDto;
import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.IService.IProductoService;
import com.nikolas.pruebaTrinity.entity.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/producto")
public class ProductoController extends ABaseController<Producto, IProductoService> {

    private final IProductoService productoService;

    public ProductoController(IProductoService productoService) {
        super(productoService, "Producto");
        this.productoService = productoService;
    }


    @PostMapping("/crear")
    public ResponseEntity<ApiResponseDto<Producto>> crearProducto(@RequestBody ProductoDto productoDto){
        try {
            ApiResponseDto<Producto> response = productoService.crearProducto(productoDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponseDto<>( null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/productos/{productoId}/estado")
    public ResponseEntity<ApiResponseDto<Producto>> actualizarEstadoProducto(
            @PathVariable Long productoId,
            @RequestBody EstadoProducto nuevoEstado) {
        try {
            ApiResponseDto<Producto> response = productoService.actualizarEstadoProducto(productoId, nuevoEstado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(null, e.getMessage(), false));
        }
    }

}
