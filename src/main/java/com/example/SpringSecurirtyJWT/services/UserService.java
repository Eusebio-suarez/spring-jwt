package com.example.SpringSecurirtyJWT.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SpringSecurirtyJWT.dto.request.UserRequestDTO;
import com.example.SpringSecurirtyJWT.dto.response.UserResponseDTO;
import com.example.SpringSecurirtyJWT.entity.Erol;
import com.example.SpringSecurirtyJWT.entity.RolEntity;
import com.example.SpringSecurirtyJWT.entity.UserEntity;
import com.example.SpringSecurirtyJWT.repository.UserRepository;

@Service
public class UserService {
    
    //encriptra contraseñas
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    public UserResponseDTO registrarUsuario(UserRequestDTO userRequest){
        //roles que se van a 
        Set<RolEntity> roles;
        //verificar que almenos tenga un rol
        //si no tine se le da el rol de usuario por defecto
        if(userRequest.getRoles()==null || userRequest.getRoles().isEmpty()){
            roles = Set.of(RolEntity.builder()
            .name(Erol.USER)
            .build());
        }
        // obtener los roles de la peticion
        // con el get roles se obtiene una coleccionde strings luego se mapean y se vueleven E rol entidad
        else{
        //si se obtubieron roles entonces se mapean a eolEntity
        roles = userRequest.getRoles().stream()
        //mapear los roles del request para pasarlos a una entidad roles
                .map(rol -> RolEntity.builder()
                    .name(Erol.valueOf(rol.toUpperCase()))
                    .build()
                )
                .collect(Collectors.toSet());
        }      
        
        //contruir la entidad user en base a el user request
        UserEntity user = UserEntity.builder()
                .username(userRequest.getUserName())
                .email(userRequest.getEmail())
                //encriptar la contraseña que se va a guardar en la bd
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(roles)
                .build();

        try{
            //hacer el registro en la base de datos        
            UserEntity userSaved =userRepository.save(user);

            //mapear el usuario guardado y pasarlo a el userResponse
            return  UserResponseDTO.builder()
                    .email(userSaved.getEmail())
                    .userName(userSaved.getUsername())
                    .build(); 
        }
        catch(Exception e){
            throw new RuntimeException("Error al registrar usuario: " + e.getMessage(), e);        
        }
           
    }

}
