// package com.example.blogplatform.config;

// import com.example.blogplatform.service.UserService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import
// org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

// private final UserService userService;
// private final PasswordEncoder passwordEncoder;

// public SecurityConfig(UserService userService, PasswordEncoder
// passwordEncoder) {
// this.userService = userService;
// this.passwordEncoder = passwordEncoder;
// }

// @Bean
// public DaoAuthenticationProvider authenticationProvider() {
// DaoAuthenticationProvider authProvider = new
// DaoAuthenticationProvider(userService);
// authProvider.setPasswordEncoder(passwordEncoder);
// return authProvider;
// }

// @Bean
// public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// http
// .authenticationProvider(authenticationProvider())
// .authorizeHttpRequests(authz -> authz
// .requestMatchers("/", "/register", "/login", "/css/**", "/js/**",
// "/h2-console/**").permitAll()
// .requestMatchers("/articles/create/**").authenticated()
// .anyRequest().permitAll())
// .formLogin(form -> form
// .loginPage("/login")
// .defaultSuccessUrl("/")
// .permitAll())
// .logout(logout -> logout
// .logoutSuccessUrl("/")
// .permitAll())
// .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
// .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

// return http.build();
// }
// }