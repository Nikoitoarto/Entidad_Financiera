package com.nikolas.pruebaTrinity.service;

import com.nikolas.pruebaTrinity.IService.IBaseService;
import com.nikolas.pruebaTrinity.entity.ABaseEntity;
import com.nikolas.pruebaTrinity.iRepository.IBaseRepository;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public abstract class ABaseService<T extends ABaseEntity> implements IBaseService<T> {

    protected abstract IBaseRepository<T, Long> getRepository();


    @Override
    public List<T> all() {
        return getRepository().findAll();
    }

    @Override
    public List<T> findByStateTrue() {
        List<T> allEntities = getRepository().findAll(); // Obtén todas las entidades
        return allEntities.stream() // Filtra las entidades donde 'state' es true
                .filter(entity -> entity.getState() != null && entity.getState())
                .collect(Collectors.toList());
    }

    @Override
    public T findById(Long id) throws Exception {
        Optional<T> op = getRepository().findById(id);

        if (op.isEmpty()) {
            throw new Exception("Registro no encontrado");
        }

        return op.get();
    }

    @Override
    public T save(T entity) throws Exception {
        try {
            entity.setCreatedAt(LocalDateTime.now());
            entity.setState(true);
            return getRepository().save(entity);
        }catch (Exception e) {
           throw new Exception("Error al guardar el entidad: " + e.getMessage());
        }
    }

    @Override
    public void update (Long id, T entity) throws Exception {
        Optional<T> op = getRepository().findById(id);

        if (op.isEmpty()) {
            throw new Exception("Registro no encontrado");
        } else if (op.get().getDeletedAt() != null) {
            throw new Exception("Registro inhabilitado");

        }

        T entityUpdate = op.get();

        String[] ignoreProperties = {"id","createdAt","deletedAt"};
        BeanUtils.copyProperties(entity, entityUpdate, ignoreProperties);
        entityUpdate.setUpdateAt(LocalDateTime.now());
        getRepository().save(entityUpdate);
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<T> op = getRepository().findById(id);

        if (op.isEmpty()) {
            throw new Exception("Registro no encontrado");
        }

        T entityUpdate = op.get();
        entityUpdate.setDeletedAt(LocalDateTime.now());
        getRepository().save(entityUpdate);
    }



}  
