package com.example.SpringSecurirtyJWT.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDTO {
    @Email
    private String email;

    @NotBlank
    private String userName;
    
    @NotBlank
    private String password;

    @NotNull(message="no se encontro la propiedad roles")
    private Set<String> roles;

}
