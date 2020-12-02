package com.udemy.project.rest.controller;

import com.udemy.project.domain.entity.Pedido;
import com.udemy.project.rest.dto.PedidoDTO;
import com.udemy.project.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService  pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

}
