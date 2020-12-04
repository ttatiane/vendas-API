package com.udemy.project.config;

import com.udemy.project.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // gera hash diferente a cada solicitação
    }

    @Override
    // authentication
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
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
            .httpBasic();

    }
}
