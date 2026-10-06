package com.deliverytech.delivery_api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;

import com.deliverytech.delivery_api.security.JwtAuthenticationFilter;
import com.deliverytech.delivery_api.service.AuthService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
            .cors(cors ->
                cors.configurationSource(corsConfigurationSource())
            )

            .csrf(AbstractHttpConfigurer::disable)

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authenticationProvider(authenticationProvider())

            .authorizeHttpRequests(auth -> auth

                // Arquivos do site
                .requestMatchers(
                    "/",
                    "/**.html",
                    "/**.css",
                    "/**.js",
                    "/**.ico"
                ).permitAll()

                // Documentação da API (Swagger)
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // Login e cadastro
                .requestMatchers("/api/auth/**").permitAll()

                // Restaurantes e produtos: qualquer pessoa pode VER, mas mexer exige login
                .requestMatchers(HttpMethod.GET, "/restaurantes", "/restaurantes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/restaurantes").permitAll()
                .requestMatchers(HttpMethod.GET, "/produtos", "/produtos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/produtos").permitAll()

                // A página de cadastro de restaurante ainda cria o restaurante antes do login.
                // Na Fase 3 isso passa para dentro da API e esta linha sai.
                .requestMatchers(HttpMethod.POST, "/restaurantes").permitAll()

                // Monitoramento: só o que o painel e o Prometheus precisam
                .requestMatchers(
                    "/actuator/health",
                    "/actuator/health/**",
                    "/actuator/info",
                    "/actuator/metrics",
                    "/actuator/metrics/**",
                    "/actuator/prometheus"
                ).permitAll()

                .requestMatchers("/dashboard", "/dashboard/**", "/dashboard.html").permitAll()
                .requestMatchers("/clientes/status").permitAll()

                // Tudo o que não está acima exige login
                // (inclui GET /clientes e /clientes/cache/limpar, que antes eram abertos)
                .anyRequest().authenticated()
            )

            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Só o seu site (Live Server) pode chamar a API pelo navegador
        configuration.setAllowedOrigins(
                List.of(
                    "http://127.0.0.1:5500",
                    "http://localhost:5500"
                )
        );

        configuration.setAllowedMethods(
                List.of(
                    "GET",
                    "POST",
                    "PUT",
                    "DELETE",
                    "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}