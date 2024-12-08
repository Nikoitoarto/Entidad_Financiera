package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Enum.EstadoProducto;
import com.nikolas.pruebaTrinity.entity.Producto;
import com.nikolas.pruebaTrinity.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/producto")
public class ProductoController extends ABaseController<Producto, ProductoService> {

    private final ProductoService productoService;

    public ProductoController(ProductoService service, ProductoService productoService) {
        super(service, "Producto");
        this.productoService = productoService;
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
