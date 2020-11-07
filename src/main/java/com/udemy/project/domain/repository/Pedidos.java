package com.udemy.project.domain.repository;

import com.udemy.project.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer > {
}
