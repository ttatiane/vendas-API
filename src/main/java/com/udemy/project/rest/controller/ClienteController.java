package com.udemy.project.rest.controller;

import com.udemy.project.domain.entity.Cliente;
import com.udemy.project.domain.repository.Clientes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
