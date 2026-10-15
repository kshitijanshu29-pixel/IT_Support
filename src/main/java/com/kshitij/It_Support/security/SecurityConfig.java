package com.kshitij.It_Support.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration 
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration ) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> 
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
        .exceptionHandling(exception -> 
            exception.accessDeniedHandler((request, response, accessDeniedException) -> response.sendError(403))
            .authenticationEntryPoint((request, response, authenticationException) -> response.sendError(401))
        )
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/auth/login" , "/auth/register").permitAll()
            //ticket controller
            .requestMatchers(HttpMethod.POST, "/ticket/*")
            .hasAnyRole("CUSTOMER")
            .requestMatchers(HttpMethod.GET , "/ticket/getmytickets")
            .hasAnyRole("CUSTOMER")
            .requestMatchers(HttpMethod.GET, "/ticket/*"  )
            .hasAnyRole("ADMIN", "SUPPORT", "CUSTOMER")
            
            .requestMatchers(HttpMethod.PUT, "/ticket/acceptticket")
            .hasRole("SUPPORT")
            
            .requestMatchers(HttpMethod.PUT, "/ticket/updateticket/*")
            .hasRole("ADMIN")

            // user
            .requestMatchers(HttpMethod.POST, "/user").permitAll()
            .requestMatchers(HttpMethod.POST,"/staffUser").hasAnyRole("ADMIN")
            
.requestMatchers(HttpMethod.GET, "/users/email").hasRole("ADMIN")
.requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
.requestMatchers(HttpMethod.GET, "/users/*").hasRole("ADMIN")

.requestMatchers(HttpMethod.PUT, "/user/*").hasRole("ADMIN")
.requestMatchers(HttpMethod.DELETE, "/user/*").hasRole("ADMIN")
.anyRequest().authenticated()
        )
.addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }

}
