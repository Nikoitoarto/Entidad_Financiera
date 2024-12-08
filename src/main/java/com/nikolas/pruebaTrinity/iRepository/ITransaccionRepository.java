package com.nikolas.pruebaTrinity.iRepository;

import com.nikolas.pruebaTrinity.entity.Transaccion;
import org.springframework.stereotype.Repository;


@Repository
public interface ITransaccionRepository extends IBaseRepository<Transaccion, Long> {
}
