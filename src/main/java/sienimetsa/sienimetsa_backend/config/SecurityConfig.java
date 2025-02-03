package sienimetsa.sienimetsa_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sienimetsa.sienimetsa_backend.web.AppuserLoginServiceImpl;
import sienimetsa.sienimetsa_backend.web.UserDetailServiceImpl;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailsService; // Service for loading backend user details.
    private final AppuserLoginServiceImpl appuserDetailsService; // Service for loading mobile user details.

    
     // Constructor injection for UserDetailServiceImpl and AppuserDetailServiceImpl.
    public SecurityConfig(UserDetailServiceImpl userDetailsService, AppuserLoginServiceImpl appuserDetailsService) {
        this.userDetailsService = userDetailsService;
        this.appuserDetailsService = appuserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console (can be deleted when postgre is added)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/login", "/h2-console/**").permitAll() // public access
                        .requestMatchers("/api/mobile/signup", "/api/mobile/login").permitAll() // Allow mobile sign-up and login
                        .requestMatchers("/api/mobile/**").authenticated() // Mobile authenticated endpoints
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
    public BCryptPasswordEncoder passwordEncoder() {
        // Provides a BCryptPasswordEncoder bean for securely hashing and verifying passwords.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider backendAuthenticationProvider() {
        // Creates a DaoAuthenticationProvider for backend users.
        // This provider uses the userDetailsService to load backend user details
        // and the passwordEncoder to securely hash and verify passwords.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider mobileAuthenticationProvider() {
        // Creates a DaoAuthenticationProvider for mobile users.
        // This provider uses the appuserDetailsService to load mobile user details
        // and the passwordEncoder to securely hash and verify passwords.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(appuserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    
    @Bean
    public AuthenticationManager authenticationManager() {
        //allows the application to authenticate users for both backend and mobile endpoints
        return new ProviderManager(List.of(backendAuthenticationProvider(), mobileAuthenticationProvider()));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
         // Configures CORS (Cross-Origin Resource Sharing) for the application.
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}