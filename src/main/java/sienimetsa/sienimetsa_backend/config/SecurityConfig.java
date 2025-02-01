package sienimetsa.sienimetsa_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sienimetsa.sienimetsa_backend.web.AppuserDetailServiceImpl;
import sienimetsa.sienimetsa_backend.web.UserDetailServiceImpl;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImpl userDetailsService; // For backend users

    @Autowired
    private AppuserDetailServiceImpl appuserDetailsService; // For mobile app users

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**") // Disable CSRF for H2 console
            )
            
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/login", "/signup", "/h2-console/**").permitAll() // Public endpoints
                .requestMatchers("/api/backend/**").hasAnyRole("ADMIN", "USER") // Backend endpoints require roles
                .requestMatchers("/api/mobile/**").permitAll() // Mobile endpoints are public
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable()) // Allow H2 console to be embedded in a frame
            )
            
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Custom login page
                .defaultSuccessUrl("/index", true) // Redirect to index on successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Custom logout URL
                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
                .invalidateHttpSession(true) // Invalidate session
                .clearAuthentication(true) // Clear authentication
                .permitAll()
            );

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        auth.userDetailsService(appuserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}