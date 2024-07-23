package com.dsj.imoveis.lib.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString
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
    @MapsId
    private Immobile immobile;

}
