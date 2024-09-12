package com.enigmacamp.warung_makan_bahari_api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    private final String jwtSecret="secret";
    private final String appName="Warung Makan Bahari";
//    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public String generateToken(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            String token = JWT.create()
                    .withSubject(userId)
                    .withIssuer(appName)
                    .withExpiresAt(Instant.now().plusSeconds(60*60))
                    .withIssuedAt(Instant.now())
                    .withClaim("role","ROLE_CUSTOMER")
                    .sign(algorithm);

            return token;
        }catch (JWTCreationException exception){
            log.error("error while generating token", exception.getMessage());
            throw new RuntimeException("error while generating token");
        }
    }

    public Boolean verifyToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException exception){
            log.error("invalid verification JWT: ", exception.getMessage());
            return false;
        }
    }

    public Map<String, Object> getUserInfoByToken(String token) {
        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            decodedJWT = verifier.verify(token);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userId", decodedJWT.getSubject());
            userInfo.put("role", decodedJWT.getClaim("role").asString());

            return userInfo;
        } catch (JWTVerificationException exception){
            log.error("invalid verification JWT: ", exception.getMessage());
            return null;
        }
    }
}
