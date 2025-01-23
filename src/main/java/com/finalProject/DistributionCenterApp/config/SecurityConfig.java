package com.finalProject.DistributionCenterApp.config;


import com.finalProject.DistributionCenterApp.models.User;
import com.finalProject.DistributionCenterApp.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Configuration
@EnableMethodSecurity
public class SecurityConfig{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException(username);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/admin/distribution")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/admin/requested_items")).hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(login ->
                        login
                                .loginPage("/login")
                                .defaultSuccessUrl("/admin/distribution", true)
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/logout")
                )
                .headers(headers ->
                        headers
                                .frameOptions().disable()
                );

        http.csrf().disable();
        return http.build();
    }
}

