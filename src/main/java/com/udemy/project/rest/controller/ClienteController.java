package com.udemy.project.rest.controller;

import com.udemy.project.domain.entity.Cliente;
import com.udemy.project.domain.repository.Clientes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping( "/{id}" )
    @ResponseBody
    public ResponseEntity<Cliente> getClienteById( @PathVariable("id") Integer id ) {
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()) {
            //ResponseEntity<Cliente> responseEntity = new ResponseEntity<>(cliente.get(), HttpStatus.OK);
            return ResponseEntity.ok(cliente.get()); // script simplificado do retorno de resposta acima
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente) {
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

}