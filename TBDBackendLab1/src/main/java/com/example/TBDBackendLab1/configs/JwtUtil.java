package com.example.TBDBackendLab1.configs;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class JwtUtil {

    //temporal place to the secret code
    private static String SecretCode = "password";
    private static Algorithm ALGORITHM = Algorithm.HMAC256(SecretCode);

    //Create a JWT with useremail, the alternative is use the id
    public String create(String useremail){
        return JWT.create()     //init JWT creation
                .withSubject(useremail)      //
                .withIssuer("app")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(60)))
                .sign(ALGORITHM);
    }

    //Verify the jwt
    public boolean isValid(String jwt){
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    //Get useremail from the jwt
    public String getUseremail(String jwt){
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }
}