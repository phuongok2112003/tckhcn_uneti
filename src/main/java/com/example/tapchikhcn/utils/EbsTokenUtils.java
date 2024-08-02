package com.example.tapchikhcn.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.tapchikhcn.entity.UserEntity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.stream.Collectors;

import static com.example.tapchikhcn.constans.enums.Variables.*;


public class EbsTokenUtils {
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

    public static String createAccessToken(UserEntity user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String createRefreshToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME))
                .sign(algorithm);
    }
}
