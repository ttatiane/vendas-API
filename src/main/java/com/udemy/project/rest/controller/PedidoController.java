package com.udemy.project.rest.controller;

import com.udemy.project.domain.entity.ItemPedido;
import com.udemy.project.domain.entity.Pedido;
import com.udemy.project.rest.dto.InformacaoItemPedidoDTO;
import com.udemy.project.rest.dto.InformacoesPedidoDTO;
import com.udemy.project.rest.dto.PedidoDTO;
import com.udemy.project.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return pedidoService.obterPedidoCompleto(id)
                .map( p -> converter(p) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .itemsPedidoDTOS(converter(pedido.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) {
            return Collections.emptyList();
        }
        return itens
                .stream()
                .map( item -> InformacaoItemPedidoDTO
                                .builder()
                                .descricaoProduto(item.getProduto().getDescricao())
                                .precoUnitario(item.getProduto().getPreco())
                                .quantidade(item.getQuantidade())
                                .build()
                ).collect(Collectors.toList());
    }

}
