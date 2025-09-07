package com.example.SpringSecurirtyJWT.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//definir la clase como una configuracion
public class SecurityConfig {
    
    //metodo que configura la cadena de filtros
    //cuando se declara un @Bean, NO necesitas instanciarlo manualmente. Spring se encarga de todo.
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            //metodo de seguridad que protege contra ataques de falsificacion 
            //se usa cunado se trabaja con sesiones o otos metodos de autenticaccion
            .csrf(csrf -> csrf.disable())
            //personalizar los endpoints que estan protegidos
            .authorizeHttpRequests(auth ->{
                //permitir el acceso a el edpoint hello
                auth.requestMatchers("/api/v1/users/hello").permitAll();
                //los demas endpoints requieren que este autenticado
                auth.anyRequest().authenticated();
            })
            .sessionManagement(session ->{
                //se define que no se va a usar una session 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            //se maneja autenticcacion basica con un usuario en memoria
            .httpBasic(Customizer.withDefaults())
            .build();
    }

    //usuario que se crea en memoria para poder ser usado en la autenticaccion basica
    //para que el usuario funcione debe estar en un objeto que lo administre :>
    @Bean
    UserDetailsService userDetailsService(){
        //perrite crear un usuario solo persiste en memoria. para usuarlo en pruebas
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("eusebio")
                .roles()
                .password(passwordEncoder().encode("123"))
                .build()); 
        return manager;
    }

    //metodo que va a ser utilizado para encriptar contraseñas
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //objeto que se encarga de la administracion de autenticcaion de usuarios
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        /*crear la autentication manager de manera 
        automatica mapeando en el aplication context
        los metodos que devuelven el userdetail service
        y el metodo para encriptar contraseñas
        */
        return authConfig.getAuthenticationManager();
    }

    /* anteriormente se definia pasando los datos manualmente
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http,
                                           UserDetailsService userDetailsService,
                                           PasswordEncoder passwordEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(userDetailsService) // se pasa manualmente
            .passwordEncoder(passwordEncoder// se pasa manualmente)
            .and()
            .build();
}
    */
    
}