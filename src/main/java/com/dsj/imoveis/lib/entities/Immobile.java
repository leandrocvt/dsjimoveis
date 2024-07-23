package com.dsj.imoveis.lib.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@ToString
@Table(name = "tb_immobile")
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double totalArea;
    private Double privateArea;
    private Integer suites;
    private Integer bedrooms;
    private Integer garage;
    private String description;
    private List<String> characteristics;
    private Double iptu;
    private Double salePrice;
    private Double rentPrice;
    private String subtype;
    private List<String> imageUrls;

    @OneToOne(mappedBy = "immobile", cascade = CascadeType.ALL)
    private Address address;

}
