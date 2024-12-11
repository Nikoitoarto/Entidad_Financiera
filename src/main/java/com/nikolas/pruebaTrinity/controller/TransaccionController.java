package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.Dto.ApiResponseDto;
import com.nikolas.pruebaTrinity.Dto.TransaccionDto;
import com.nikolas.pruebaTrinity.IService.ITransaccionService;
import com.nikolas.pruebaTrinity.entity.Transaccion;
import com.nikolas.pruebaTrinity.service.TransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/transaccion")
public class TransaccionController extends ABaseController<Transaccion, ITransaccionService>{

    private final ITransaccionService transaccionService;

    public TransaccionController(ITransaccionService transaccionService) {
        super(transaccionService, "Transaccion");
        this.transaccionService = transaccionService;
    }

    @PostMapping("/crear")
    public ResponseEntity<ApiResponseDto<Transaccion>> crearTransaccion(@RequestBody TransaccionDto transaccionDto){
        try {
            ApiResponseDto<Transaccion> response = transaccionService.crearTransaccion(transaccionDto);
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponseDto<>( null, e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

}
