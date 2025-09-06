package com.example.SpringSecurirtyJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurirtyJWT.entity.RolEntity;

@Repository // definir l clse como un repositorio
public interface RolReposittory extends JpaRepository<RolEntity, Long> {
    //metodo par buscra por nombre
    Optional<RolEntity> findByname(String name);
}
