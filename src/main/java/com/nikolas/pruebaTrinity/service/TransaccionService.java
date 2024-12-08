package com.nikolas.pruebaTrinity.service;


import com.nikolas.pruebaTrinity.IService.ITransaccionService;
import com.nikolas.pruebaTrinity.entity.Transaccion;
import com.nikolas.pruebaTrinity.iRepository.IBaseRepository;
import com.nikolas.pruebaTrinity.iRepository.ITransaccionRepository;
import org.springframework.stereotype.Service;


@Service
public class TransaccionService extends ABaseService<Transaccion> implements ITransaccionService{

    private final ITransaccionRepository transaccionRepository;

    public TransaccionService(ITransaccionRepository transaccionRepository){
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    protected IBaseRepository<Transaccion, Long> getRepository() {
        return transaccionRepository;
    }


}
