package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.entity.Transaccion;
import com.nikolas.pruebaTrinity.service.TransaccionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/transaccion")
public class TransaccionController extends ABaseController<Transaccion, TransaccionService>{
    public TransaccionController(TransaccionService service) {
        super(service, "Transaccion");
    }
}
