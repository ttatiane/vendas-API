package com.udemy.project.rest.controller;

import com.udemy.project.domain.entity.Produto;
import com.udemy.project.domain.repository.ProdutosRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutosRepository produtosRepository;

    public ProdutoController(ProdutosRepository produtosRepository) {
        this.produtosRepository = produtosRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto save (@RequestBody  Produto produto) {
        return produtosRepository.save(produto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody Produto produto ) {
        produtosRepository
                .findById(id)
                .map(p -> {
                    produto.setId(p.getId());
                    produtosRepository.save(produto);
                    return produto;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        produtosRepository
                .findById(id)
                .map( p -> {
                    produtosRepository.delete(p);
                    return Void.TYPE;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping("{id}")
    public Produto getById(@PathVariable Integer id) {
        return produtosRepository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping
    public List<Produto> find(Produto filtro) {
        ExampleMatcher exampleMatcher = ExampleMatcher
                                            .matching()
                                            .withIgnoreCase()
                                            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, exampleMatcher);
        return produtosRepository.findAll(example);
    }

}
