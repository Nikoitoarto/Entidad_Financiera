package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.entity.Cliente;
import com.nikolas.pruebaTrinity.service.ClienteService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/cliente")
public class ClienteController extends ABaseController<Cliente, ClienteService>{

    public ClienteController(ClienteService service) {
        super(service, "Cliente");
    }
}
