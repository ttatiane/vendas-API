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
            clientes.salvar(new Cliente("Douglas"));
            clientes.salvar(new Cliente("outro cliente"));

            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("\nAtualizando clientes =============== ");
            todosClientes.forEach(c -> {
                c.setNome( c.getNome() + " atualizado." );
                clientes.atualizar(c);
            });
            todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("\nBuscando clientes ===============  ");
            clientes.buscarPorNome("cli").forEach(System.out::println);

//            System.out.println("\nDeletando clientes ============== ");
//            clientes.obterTodos().forEach( c -> { clientes.deletar(c); });

            System.out.println("\nBuscando por todos os clientes ============= ");
            todosClientes = clientes.obterTodos();
            if(todosClientes.isEmpty()) {
                System.out.println("Nenhum cliente encontrado.");
            } else {
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
