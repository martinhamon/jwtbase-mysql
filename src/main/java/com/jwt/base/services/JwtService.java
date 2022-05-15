/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jwt.base.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import static com.jwt.base.constants.Constant.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 *
 * @author MH
 */
@Service

public class JwtService {
  @Value("${jwt.secret}")
private String secret;
     public String createToken (String user, List <String> roles){
         
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date ())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLESECONDS))
                .withClaim(USER, user)
                .withArrayClaim(ROLES, roles.toArray(new String[0]))
                .sign(Algorithm.HMAC256(secret));
     }
     
     public boolean isBearer (String authorization){
         return authorization != null && authorization.startsWith(BEARER) &&
                 authorization.split("\\.").length==3;
     }
     
     public String user (String authorization) throws Exception{
         return this.verify(authorization).getClaim(USER).asString();
     }
     
     private DecodedJWT verify (String authorization) throws Exception
     {
         if (!this.isBearer(authorization))
         {
             throw new Exception("No valido");
         }
         try {
             JWTVerifier verifier= JWT.require(Algorithm.HMAC256(secret))
                     .withIssuer(ISSUER)
                     .build();
            return    verifier.verify(authorization.substring(7));
         } catch (Exception e) {
             throw new Exception("No valido");
         }
     }
     
     public List<String> roles (String authorization ) throws Exception{
         return Arrays.asList(this.verify(authorization).getClaim(ROLES).asArray(String.class));
     }
    
}
