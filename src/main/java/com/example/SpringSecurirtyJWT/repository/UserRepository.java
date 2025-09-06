package com.example.SpringSecurirtyJWT.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringSecurirtyJWT.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    //metodo para buscar por nombre de usuario
    Optional<UserEntity> findByUsername(String username);
}
