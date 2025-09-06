package com.example.SpringSecurirtyJWT.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data //getters y setters
@AllArgsConstructor
@NoArgsConstructor
@Builder //patron builder
@Entity // definir la clse como una entidad
@Table(name="users")
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

    //hacer la relacion entre tablas suario y roles
    //fetch eager simepre trela la informacion de los roles
    //target etity es la tabla con la que se va a relacionar
    //con el cascade persist se evita que se borren los registros de los roles cuando se modifique el usuario
    @ManyToMany(fetch=FetchType.EAGER,targetEntity=RolEntity.class,cascade=CascadeType.PERSIST)
    //roles del usuario
    //se utiliza set para que no s erepitan los roles en el usuario
    //se debe crar una table puente por que la relacion es de muchos a muchos
    //se creo una tabla puente user_roles que relaciona co el join column relaciona el id de la entidad ser_id con el id del rol rol_id
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="rol_id"))
    private Set<RolEntity> roles;
}
