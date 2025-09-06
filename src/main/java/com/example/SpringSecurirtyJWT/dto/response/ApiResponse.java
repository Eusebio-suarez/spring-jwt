package com.example.SpringSecurirtyJWT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getters y setters con lombuk
@Builder //patron builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {
    //informar si la peticion fue exitosa
    private boolean success;
    //mensaje
    private String message;
    //informacion que va devolver que va a devolver
    private T data;
}
