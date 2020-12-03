package com.udemy.project.service;

import com.udemy.project.domain.entity.Pedido;
import com.udemy.project.domain.enums.StatusPedido;
import com.udemy.project.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
