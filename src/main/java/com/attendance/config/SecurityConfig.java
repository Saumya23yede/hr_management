package com.attendance.config;

import com.attendance.model.CustomUserDetails; // Ensure this class exists
import com.attendance.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final com.attendance.repository.UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(CustomUserDetails::new) // Wrap your User entity in CustomUserDetails
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService()); // Set user details service
        provider.setPasswordEncoder(passwordEncoder()); // Set password encoder
        return provider;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity, but re-enable in production
                .authorizeRequests(requests -> requests
                        .requestMatchers("/users/create", "/api/auth/register", "/register", "/login").permitAll() // Allow
                                                                                                                   // public
                                                                                                                   // access
                        // to register and
                        // login
                        // pages
                        .anyRequest().authenticated()) // Require authentication for all other requests
                .formLogin(form -> form
                        .loginPage("/login") // Use the custom login page
                        .permitAll() // Allow everyone to access the login page
                        .defaultSuccessUrl("/dashboard", true) // Redirect to /dashboard on successful login
                        .failureUrl("/login?error=true") // Redirect back to login with error flag if login fails
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Custom logout URL
                        .logoutSuccessUrl("/login?logout=true") // Redirect to login page on logout
                        .permitAll()); // Allow everyone to log out

        return http.build();
    }
}
