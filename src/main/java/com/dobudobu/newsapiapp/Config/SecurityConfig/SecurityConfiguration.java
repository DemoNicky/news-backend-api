package com.dobudobu.newsapiapp.Config.SecurityConfig;

import com.dobudobu.newsapiapp.Entity.Enum.Role;
import com.dobudobu.newsapiapp.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**",
                                "/api/v1/article/get-article",
                                "/api/v1/article/hit-article-detail/**",
                                "/api/v1/category/get-article-by-category/**",
                                "/api/v1/article/search-article").permitAll()

                        //admin
                        .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/category/create-category").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/article/create-article").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/article/update-article/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/article/delete-article/**").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/article/activate-article/**").hasAnyAuthority(Role.ADMIN.name())

                        //user
                        .requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/api/v1/comment/comment-article/**").hasAnyAuthority(Role.USER.name())



                        .anyRequest().authenticated())

                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationPovider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
                return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationPovider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
