package com.udemy.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // gera hash diferente a cada solicitação
    }

    @Override
    // authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("fulano")
                .password(passwordEncoder().encode("123"))
                .roles("USER", "ADMIN"); // perfil de usuário
    }

    @Override
    // authorization
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()   // csfr, configuração de segurança entre app web e backend, como estamos usando api rest (stateless) não é necessário
            .authorizeRequests()
                .antMatchers("/api/clientes/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/pedidos/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/produtos/**").hasAnyRole("ADMIN")
        .and()
            .formLogin();

    }
}
