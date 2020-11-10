package com.udemy.project.domain.repository;

import com.udemy.project.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository< Produto, Integer > {
}
