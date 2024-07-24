package com.dsj.imoveis.lib.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String neighborhood;
    private String city;
    private String state;
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "immobile_id")
    private Immobile immobile;

}
