package in.bushansirgur.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for development
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().permitAll())
                // .formLogin(login -> login.disable()) // Disable form login
                .httpBasic(basic -> basic.disable()); // Disable HTTP Basic Authentication
        return http.build();
    }
}
