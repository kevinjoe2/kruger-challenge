package com.kchamorro.krugerchallenge.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CustomDecodeJWT {
    public static String getUsername(String token){
        String tokenDecode = token.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("d5e1c470-4ee0-4e9b-ab5e-1ef14bcf8b10".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(tokenDecode);
        return decodedJWT.getSubject();
    }
}
