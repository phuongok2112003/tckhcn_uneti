package com.example.tapchikhcn.configuration;


import com.example.tapchikhcn.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${endpoints.cors.allowed-headers: Authorization, Content-Type}")
    String allowedHeaders;
    @Value("${endpoints.cors.allowed-methods: POST, PUT, GET, OPTIONS, DELETE}")
    List<String> allowedMethods;
    @Value("${endpoints.cors.allowed-origins: *}")
    String allowedOrigins;
    @Value("${endpoints.cors.allowed-credentials: true}")
    boolean allowedCredentials;
    @Value("${endpoints.cors.max-age: 36000}")
    long maxAge;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenBlacklist tokenBlacklist;
    private final UserService userService;
    private static final String[] PUBLIC_URLS = {
            "/api/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new CustomAuthorizationFilter(tokenBlacklist), UsernamePasswordAuthenticationFilter.class);

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler(userService));
        customAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler(userService));
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.addFilter(customAuthenticationFilter);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(allowedOrigins);
        allowedMethods.forEach(configuration::addAllowedMethod);
        configuration.addAllowedHeader(allowedHeaders);
        configuration.setAllowCredentials(allowedCredentials);
        configuration.setMaxAge(maxAge);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);

        return provider;
    }
}
