package com.nikolas.pruebaTrinity.iRepository;

import com.nikolas.pruebaTrinity.entity.Cliente;
import org.springframework.stereotype.Repository;


@Repository
public interface IClienteRepository extends IBaseRepository<Cliente, Long>  {

}
