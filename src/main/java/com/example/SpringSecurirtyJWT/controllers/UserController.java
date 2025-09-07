package com.example.SpringSecurirtyJWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringSecurirtyJWT.dto.request.UserRequestDTO;
import com.example.SpringSecurirtyJWT.dto.response.ApiResponse;
import com.example.SpringSecurirtyJWT.dto.response.UserResponseDTO;
import com.example.SpringSecurirtyJWT.services.UserService;

import jakarta.validation.Valid;

@RestController//definir la calse como un controllador
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/helloSecure")
    public String helloSecured(){
        return "hello world secured";
    }

    //endpoint para crear un usuario
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>>crearUsuario(@Valid @RequestBody UserRequestDTO userRequest ){

        try {
            //registar el usuario por medio del servicio
            UserResponseDTO user = userService.registrarUsuario(userRequest);

            //responder con un codigo de estado creado y con la informacin adicional de la peticion
            return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.builder()
                    .success(true)
                    .message("usuario creado correctamente")
                    .data(user)
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
