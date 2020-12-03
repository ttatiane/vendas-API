package com.udemy.project.service.impl;

import com.udemy.project.domain.entity.Cliente;
import com.udemy.project.domain.entity.ItemPedido;
import com.udemy.project.domain.entity.Pedido;
import com.udemy.project.domain.entity.Produto;
import com.udemy.project.domain.enums.StatusPedido;
import com.udemy.project.domain.repository.ClientesRepository;
import com.udemy.project.domain.repository.ItensPedidoRepository;
import com.udemy.project.domain.repository.PedidosRepository;
import com.udemy.project.domain.repository.ProdutosRepository;
import com.udemy.project.exception.PedidoNaoEncontradoException;
import com.udemy.project.exception.RegraNegocioException;
import com.udemy.project.rest.dto.ItemPedidoDTO;
import com.udemy.project.rest.dto.PedidoDTO;
import com.udemy.project.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItensPedidoRepository itensPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente clienteEncontrado = clientesRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedidoPopulado = new Pedido();
        pedidoPopulado.setTotal(pedidoDTO.getTotal());
        pedidoPopulado.setDataPedido(LocalDate.now());
        pedidoPopulado.setCliente(clienteEncontrado);
        pedidoPopulado.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidosConvertidos = converterItems(pedidoPopulado, pedidoDTO.getItems());
        pedidosRepository.save(pedidoPopulado);
        itensPedidoRepository.saveAll(itemPedidosConvertidos);
        pedidoPopulado.setItens(itemPedidosConvertidos);
        return pedidoPopulado;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository
                .findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidosRepository.save(pedido);
                } ).orElseThrow( () -> new PedidoNaoEncontradoException() );
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> itemPedidoDTOS) {
        if(itemPedidoDTOS.isEmpty()) {
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return itemPedidoDTOS
                .stream()
                .map( dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produtoValidado = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produtoValidado);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
