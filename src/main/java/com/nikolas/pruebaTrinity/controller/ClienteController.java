package com.nikolas.pruebaTrinity.controller;

import com.nikolas.pruebaTrinity.Dto.ClienteDto;
import com.nikolas.pruebaTrinity.IService.IClienteService;
import com.nikolas.pruebaTrinity.entity.Cliente;
import com.nikolas.pruebaTrinity.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/cliente")
public class ClienteController extends ABaseController<Cliente, IClienteService>{

    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        super(clienteService, "Cliente");
        this.clienteService = clienteService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@Valid @RequestBody ClienteDto clienteDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errores);
        }
        // Mapea el DTO a la entidad
        Cliente cliente = new Cliente();
        cliente.setNombres(clienteDTO.getNombres());
        cliente.setApellidos(clienteDTO.getApellidos());
        cliente.setTipoIdentificacion(clienteDTO.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(clienteDTO.getNumeroIdentificacion());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

        // Guarda el cliente usando el servicio
        Cliente clienteGuardado = clienteService.save(cliente);
        return ResponseEntity.ok(clienteGuardado);
    }

}
