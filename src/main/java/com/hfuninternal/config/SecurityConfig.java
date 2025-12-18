package com.hfuninternal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {}) // You can configure CORS properly if needed
            .authorizeHttpRequests(auth -> auth

                // ✅ PUBLIC APIs (no login needed)
                .requestMatchers(
                    "/api/auth/**",
                    "/api/posts/feed",
                    "/api/reels/feed",
                    "/api/users/*/profile",
                    "/api/users/*/posts",
                    "/api/users/*/reels",
                    "/api/users/*/highlights"
                ).permitAll()

                // ✅ AUTHENTICATED APIs (login required)
                .requestMatchers(
                    "/api/users/me",
                    "/api/users/profile",
                    "/api/users/upload-profile-picture",
                    "/api/users/*/follow",
                    "/api/users/*/unfollow",
                    "/api/users/*/block",
                    "/api/users/*/report",
                    "/api/posts/*/like",
                    "/api/posts/*/share",
                    "/api/reels/*/like"
                ).authenticated()

                // ❌ Everything else blocked
                .anyRequest().denyAll()
            );

        return http.build();
    }
}
