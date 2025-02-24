package sienimetsa.sienimetsa_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sienimetsa.sienimetsa_backend.jwt.JwtAuthenticationFilter;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;
import sienimetsa.sienimetsa_backend.web.AppuserService;
import sienimetsa.sienimetsa_backend.web.UserDetailServiceImpl;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailsService;
    private final AppuserService appuserDetailsService;
    private final JwtUtil jwtUtil;

    public SecurityConfig(UserDetailServiceImpl userDetailsService, AppuserService appuserDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.appuserDetailsService = appuserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain mobileSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            // Include /api/profile/** in addition to /mobile/**
            .securityMatcher("/mobile/**", "/api/profile/**")
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/mobile/signup", "/mobile/login").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .formLogin(form -> form.disable())
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, appuserDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/login", "/h2-console/**").permitAll()
                .requestMatchers("/", "/frontpage", "/users", "/mushrooms").hasRole("ADMIN")
                .anyRequest().authenticated())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/frontpage", true)
                .permitAll())
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager frontAuthenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider backendAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // For backend
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider frontendAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appuserDetailsService); // For mobile
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Primary
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(backendAuthenticationProvider(), frontendAuthenticationProvider()));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));  // Add your frontend URL here
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Include OPTIONS for preflight requests
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "*")); // Allow all headers
        configuration.setAllowCredentials(true); // Allow cookies or credentials if needed
    
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}