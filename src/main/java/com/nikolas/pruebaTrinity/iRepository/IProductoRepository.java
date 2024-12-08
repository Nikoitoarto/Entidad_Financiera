package com.nikolas.pruebaTrinity.iRepository;

import com.nikolas.pruebaTrinity.entity.Producto;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductoRepository extends IBaseRepository<Producto, Long> {
}
