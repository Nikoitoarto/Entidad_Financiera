package com.nikolas.pruebaTrinity.iRepository;

import com.nikolas.pruebaTrinity.entity.ABaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface IBaseRepository <T extends ABaseEntity, ID> extends JpaRepository<T, Long> {

}
