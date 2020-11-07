package com.udemy.project.domain.repository;

import com.udemy.project.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    //Query Methods convention
    //select c from Cliente c where c.nome like :nome
    List<Cliente> findByNomeLike(String nome);

    //HQL
    @Query(value = " select c from Cliente c where c.nome like :nome ")
    List<Cliente> procurarPorNome(@Param("nome") String nome);

    //SQL nativo
    @Query(value = " select * from Cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    List<Cliente> findByNomeLikeOrIdOrderById(String nome, Integer id);

//    Cliente findOneByCpf(String cpf);

    @Query(" delete from Cliente c where c.nome = :nome ")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome);

}
