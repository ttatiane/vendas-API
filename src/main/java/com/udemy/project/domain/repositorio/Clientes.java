package com.udemy.project.domain.repositorio;

import com.udemy.project.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    //Query Methods convention
    //select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeLikeOrIdOrderById(String nome, Integer id);

//    Cliente findOneByCpf(String cpf);

    boolean existsByNome(String nome);

}
