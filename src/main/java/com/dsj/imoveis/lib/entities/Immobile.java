package com.dsj.imoveis.lib.entities;

import com.dsj.imoveis.lib.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Double iptu;
    private Double salePrice;
    private Double rentPrice;
    private String subtype;
    private Boolean highlight;
    @Enumerated(EnumType.STRING)
    private ImmobileCategory category;
    private List<String> characteristics;
    private List<String> imageUrls;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updateAt;

    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        updateAt = Instant.now();
    }

    @Enumerated(EnumType.STRING)
    private OptionImmobile option;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

}
