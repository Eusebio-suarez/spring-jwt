package com.example.SpringSecurirtyJWT.controllers;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.example.SpringSecurirtyJWT.dto.request.UserRequestDTO;
import com.example.SpringSecurirtyJWT.dto.response.ApiResponse;
import com.example.SpringSecurirtyJWT.dto.response.UserResponseDTO;
import com.example.SpringSecurirtyJWT.entity.Erol;
import com.example.SpringSecurirtyJWT.entity.RolEntity;
import com.example.SpringSecurirtyJWT.entity.UserEntity;
import com.example.SpringSecurirtyJWT.repository.UserRepository;

import jakarta.validation.Valid;

@RestController//definir la calse como un controllador
@RequestMapping("")
public class PrincipalController {
    @Autowired
    private UserRepository userRepository;
    
    @GetExchange("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/helloSecure")
    public String helloSecured(){
        return "hello world secured";
    }

    //endpoint para crear un usuario
    @PostMapping("/crear")
    public ResponseEntity<ApiResponse<?>>crearUsuario(@Valid @RequestBody UserRequestDTO userRequest ){
        //roles que se van a 
        Set<RolEntity> roles;
      
        try {
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
                    .password(userRequest.getPassword())
                    .roles(roles)
                    .build();

            //hacer el registro en la base de datos        
            UserEntity userSaved =userRepository.save(user);

            //mapear el usuario guardado y pasarlo a el userResponse
            UserResponseDTO userResponse = UserResponseDTO.builder()
                    .email(userSaved.getEmail())
                    .userName(userSaved.getUsername())
                    .build();

            //responder con un codigo de estado creado y con la informacin adicional de la peticion
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                    .success(true)
                    .message("usuario creado correctamente")
                    .data(userResponse)
                    .build());

        } catch (Exception e) {
            //manejo de errores en el catch
            return ResponseEntity.badRequest().body(ApiResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .data(null)
                    .build()
            );
        }
    }
}
