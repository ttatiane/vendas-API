package com.udemy.project;

import com.udemy.project.domain.entity.Cliente;
import com.udemy.project.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {
            System.out.println("\nSalvando clientes ================== ");
            clientes.save(new Cliente("Douglas"));
            clientes.save(new Cliente("outro cliente"));

           List<Cliente> result = clientes.encontrarPorNome("%cliente%");
           result.forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
