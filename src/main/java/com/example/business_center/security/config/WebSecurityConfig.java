package com.example.business_center.security.config;

import com.example.business_center.security.user.Role;
import com.example.business_center.security.user.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;

    private final static String[] STATIC_RESOURCES =  {
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/scripts/**",
            "/files/**",
            "favicon.ico"
    };
    private static final String[] NON_AUTHENTICATED_RESOURCES = {
            "/login",
            "/registration",
            "/offices",
            "/services",
            "/",
            "api/register"
    };
    private final static String[] USER_RESOURCES = {
            "/profile",
            "api/user",
            "api/rent",
            "api/service",
    } ;
    private final static String[] ADMIN_RESOURCES = {
            "api/admin/**",
            "offices/**",
            "services/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(STATIC_RESOURCES).permitAll()
                                .requestMatchers(NON_AUTHENTICATED_RESOURCES).permitAll()
                                .requestMatchers(USER_RESOURCES).hasAnyAuthority(Role.CLIENT.name(), Role.ADMIN.name())
                                .requestMatchers(ADMIN_RESOURCES).hasAnyAuthority(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(userDetailsService)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
