package com.dsj.imoveis.lib.entities;

import com.dsj.imoveis.lib.entities.enums.CommercialType;
import com.dsj.imoveis.lib.entities.enums.ImmobileCategory;
import com.dsj.imoveis.lib.entities.enums.ResidentialType;
import com.dsj.imoveis.lib.entities.enums.RuralType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
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
    private ImmobileCategory category;
    private List<String> imageUrls;

    @OneToOne(mappedBy = "immobile", cascade = CascadeType.ALL)
    private Address address;

    public void setSubtype(String subtype) {
        switch (this.category) {
            case RESIDENCIAL:
                if (!Arrays.asList(ResidentialType.values()).contains(ResidentialType.valueOf(subtype))) {
                    throw new IllegalArgumentException("Invalid subtype for Residential category");
                }
                break;
            case COMERCIAL:
                if (!Arrays.asList(CommercialType.values()).contains(CommercialType.valueOf(subtype))) {
                    throw new IllegalArgumentException("Invalid subtype for Commercial category");
                }
                break;
            case RURAL:
                if (!Arrays.asList(RuralType.values()).contains(RuralType.valueOf(subtype))) {
                    throw new IllegalArgumentException("Invalid subtype for Rural category");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid category");
        }
        this.subtype = subtype;
    }


}
