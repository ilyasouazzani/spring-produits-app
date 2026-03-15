package ma.enset.springproduitsapp.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .tokenValiditySeconds(86400) // 1 day
                .key("springProduitsApp")
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/webjars/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/login", "/login/**").permitAll()
                // Admin-only endpoints
                .requestMatchers("/deleteProduct/**", "/formProduct/**", "/saveProduct/**", "/editProduct/**").hasRole("ADMIN")
                .requestMatchers("/deletePatient/**", "/formPatient/**", "/savePatient/**", "/editPatient/**").hasRole("ADMIN")
                .requestMatchers("/deleteMedecin/**", "/formMedecin/**", "/saveMedecin/**", "/editMedecin/**").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/notAuthorized")
            )
            // Allow H2 console in iframes
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            // Disable CSRF for H2 console (dev only)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            );

        return http.build();
    }
}
