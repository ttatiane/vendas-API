package com.udemy.project.domain.repository;

import com.udemy.project.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensPedidoRepository extends JpaRepository< ItemPedido, Integer > {
}
