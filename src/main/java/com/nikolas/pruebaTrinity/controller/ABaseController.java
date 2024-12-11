package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.IService.IBaseService;
import com.nikolas.pruebaTrinity.entity.ABaseEntity;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ABaseController<T extends ABaseEntity, S extends IBaseService<T>> {
    protected S service;
    protected String entityName;

    protected ABaseController(S service, String entityName) {
        this.service = service;
        this.entityName = entityName;
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<T>>> findByStateTrue(){
        try{
            return ResponseEntity.ok(new ApiResponseDto<List<T>>(service.findByStateTrue(), "Datos obtenidos", true));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponseDto<List<T>>( null, e.getMessage(), false));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponseDto<T>> show(@PathVariable Long id){
        try {
            T entity = service.findById(id);
            return ResponseEntity.ok(new ApiResponseDto<T>(entity, "Registro encontrado", true));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponseDto<T>(null, e.getMessage(), false));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<T>> save(@Valid @RequestBody T entity){
        try{
            entity.setCreatedAt(LocalDateTime.now());
            return  ResponseEntity.ok(new ApiResponseDto<T>(service.save(entity), "Datos Guardados", true));

        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponseDto<>(null, e.getMessage(), false));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponseDto<T>> update(@PathVariable Long id, @RequestBody T entity){
        try{
            service.update(id, entity);
            return ResponseEntity.ok(new ApiResponseDto<T>(null, "Datos actualizados", true));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponseDto<>(null, e.getMessage(), false));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable Long id){
        try{
            service.delete(id);
            return ResponseEntity.ok(new ApiResponseDto<T>(null, "Registro eliminado", true));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new ApiResponseDto<>(null, e.getMessage(), false));
        }
    }

}
