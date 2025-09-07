package com.example.SpringSecurirtyJWT.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


/*sirve para que Spring detecte una clase y 
la convierta automáticamente en un bean disponible en el contexto,
 para que puedas inyectarla en otras partes del proyecto.
*/
@Component
public class JwtUtils {
    //data definida en el yml
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private String timeExpiration;

    //genera un token de acceso
    public String generateAccesToken(String userName){
        return Jwts.builder()
                .setSubject(userName)
                //fecha de crecaion del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //fecha de expiracion del token
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                //firmar el token con el secret key
                //Para que HS256 sea seguro, la clave debe tener al menos 256 bits (32 bytes).
                .signWith(getSignatureKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    /*este metodo grantiza que la key tenga el tamaño 
    correcto para que sea compatible con elaloritmo
    */

    public Key getSignatureKey(){
        //convierte la cadena secretKey que está en Base64 en un arreglo de bytes
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        /*Garantiza que la longitud de la clave sea suficiente 
        para el algoritmo de firma HMAC.
        esta key puede ser usada con cualquier algoritmo HS
        */
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //metodo para verificar el token
    public boolean isValidToken(String token){
        try{

        //Crea un parser de JWT
        Jwts.parser()
            // Configura la clave secreta que va a utilizar el parser
            .setSigningKey(getSignatureKey())
            // Construir un objeto JwtParser listo para usarse
            .build()
            /* el parser utiliza la clave secreta para 
             analiza el token, valida estructura, firma y expiracion
            */
            .parseSignedClaims(token);

            //si no falla nada el token es valido
            return true;
        }
        catch(Exception e){
            //el token es invalido
            return false;
        }
    }
}
