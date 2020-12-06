package com.udemy.project.domain.entity;

import com.udemy.project.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    // o SET evita repetição e é o mais indicado, o LIST está inserido neste contexto didaticamente
    private List<ItemPedido> itens;
}
