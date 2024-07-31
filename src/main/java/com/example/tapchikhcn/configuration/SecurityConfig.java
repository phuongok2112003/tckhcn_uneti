package com.example.tapchikhcn.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.signerkey}")
    private String  signerKey;
    private final String[] PUBLIC_ENDPOINT = {
            "/login",
            "/oauth/logout",
            "/oauth/refresh",
            "/oauth/token"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        Config endpoid
        http.authorizeHttpRequests(request -> request.antMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll().anyRequest().authenticated());
//        Tránh tấn công cross site
        http.csrf(AbstractHttpConfigurer::disable);
//        Config oauth2
        http.oauth2ResourceServer(oauth -> oauth.jwt(jwtSecret -> jwtSecret.decoder(jwtDecoder())));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){

        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512" );
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
    }

}
