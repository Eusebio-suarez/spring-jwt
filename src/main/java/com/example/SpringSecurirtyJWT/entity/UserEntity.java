package com.example.SpringSecurirtyJWT.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //getters y setters
@Builder //patron builder
@Entity // definir la clse como una entidad
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max=80)
    private String email;
    
    @NotBlank
    @Size(max=30)
    private String username;

    @NotBlank
    private String password;
}
