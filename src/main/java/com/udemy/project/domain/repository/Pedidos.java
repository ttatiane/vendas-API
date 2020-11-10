package com.udemy.project.domain.repository;

import com.udemy.project.domain.entity.Cliente;
import com.udemy.project.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository< Pedido, Integer > {
    List<Pedido> findByCliente( Cliente cliente );
}
