package com.dsj.imoveis.repository;

import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmobileRepository extends JpaRepository<Immobile, Long> {

    @Query("SELECT obj FROM Immobile obj " +
            "WHERE (:title IS NULL OR UPPER(obj.title) LIKE UPPER(CONCAT('%', :title, '%'))) " +
            "AND (:category IS NULL OR obj.category = :category) " +
            "AND (:subtype IS NULL OR UPPER(obj.subtype) LIKE UPPER(CONCAT('%', :subtype, '%'))) " +
            "AND (:city IS NULL OR UPPER(obj.address.city) LIKE UPPER(CONCAT('%', :city, '%'))) " +
            "AND (:state IS NULL OR UPPER(obj.address.state) LIKE UPPER(CONCAT('%', :state, '%'))) " +
            "AND (:neighborhood IS NULL OR UPPER(obj.address.neighborhood) LIKE UPPER(CONCAT('%', :neighborhood, '%'))) " +
            "AND (:minPrice IS NULL OR obj.salePrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR obj.salePrice <= :maxPrice) " +
            "AND (:bedrooms IS NULL OR obj.bedrooms = :bedrooms) " +
            "AND (:zipCode IS NULL OR UPPER(obj.address.zipCode) LIKE UPPER(CONCAT('%', :zipCode, '%'))) ")
    Page<Immobile> search(
            String title,
            ImmobileCategory category,
            String subtype,
            String city,
            String state,
            String neighborhood,
            Double minPrice,
            Double maxPrice,
            Integer bedrooms,
            String zipCode,
            Pageable pageable);

}
