package com.udemy.project;

import com.udemy.project.domain.entity.Cliente;
import com.udemy.project.domain.entity.Pedido;
import com.udemy.project.domain.repository.Clientes;
import com.udemy.project.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes,
                                  @Autowired Pedidos pedidos) {
        return args -> {
            System.out.println("\nSalvando clientes ================== ");
            Cliente fulano = new Cliente("Fulano");
            clientes.save(fulano);

            Pedido p = new Pedido();
            p.setCliente(fulano);
            p.setDataPedido( LocalDate.now() );
            p.setTotal(BigDecimal.valueOf(100));
            pedidos.save(p);

            //Obtem os pedidos, passando o codigo do cliente como parametro
            Cliente clienteFetchPedidos = clientes.findClienteFetchPedidos(fulano.getId());
            System.out.println(clienteFetchPedidos);
            System.out.println(clienteFetchPedidos.getPedidos());

            //Obtem os pedidos, passando o cliente como parametro
            pedidos.findByCliente(fulano).forEach(System.out::println);

        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
