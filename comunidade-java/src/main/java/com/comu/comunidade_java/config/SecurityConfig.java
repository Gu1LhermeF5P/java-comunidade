package com.comu.comunidade_java.config;

import com.comu.comunidade_java.security.JwtAuthenticationFilter;
import com.comu.comunidade_java.security.UserDetailsServiceImpl; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita @PreAuthorize, etc.
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // @Autowired
    // private UserDetailsServiceImpl userDetailsService; // Descomente quando UserDetailsServiceImpl for criado

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // Endpoints públicos (autenticação, documentação Swagger)
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // Permitir GET em boletins para todos
                .requestMatchers(HttpMethod.GET, "/api/boletins", "/api/boletins/**").permitAll()
                // Outras operações em boletins podem exigir autenticação ou roles específicas
                // Exemplo: .requestMatchers(HttpMethod.POST, "/api/boletins").hasRole("USER")
                // Exemplo: .requestMatchers(HttpMethod.PUT, "/api/boletins/**").hasRole("ADMIN")
                // Exemplo: .requestMatchers(HttpMethod.DELETE, "/api/boletins/**").hasRole("ADMIN")
                .anyRequest().authenticated() // Todas as outras requisições precisam de autenticação
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}