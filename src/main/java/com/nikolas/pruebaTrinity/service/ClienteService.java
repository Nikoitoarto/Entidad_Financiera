package com.nikolas.pruebaTrinity.service;

import com.nikolas.pruebaTrinity.IService.IClienteService;
import com.nikolas.pruebaTrinity.entity.Cliente;
import com.nikolas.pruebaTrinity.iRepository.IBaseRepository;
import com.nikolas.pruebaTrinity.iRepository.IClienteRepository;
import org.springframework.stereotype.Service;


@Service
public class ClienteService extends ABaseService<Cliente> implements IClienteService {

   private final IClienteRepository clienteRepository;

   public ClienteService(IClienteRepository clienteRepository) {
       this.clienteRepository = clienteRepository;
   }

    @Override
    protected IBaseRepository<Cliente, Long> getRepository() {
        return clienteRepository;
    }



}
