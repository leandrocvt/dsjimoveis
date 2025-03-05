package com.dsj.imoveis.repository;

import com.dsj.imoveis.lib.entities.Immobile;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.lib.enums.OptionImmobile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmobileRepository extends JpaRepository<Immobile, Long> {

    @Query("SELECT obj FROM Immobile obj " +
            "WHERE (:title IS NULL OR UPPER(CAST(obj.title AS string)) LIKE UPPER(CONCAT('%', CAST(:title AS string), '%'))) " +
            "AND (:category IS NULL OR obj.category = :category) " +
            "AND (:subtype IS NULL OR UPPER(CAST(obj.subtype AS string)) LIKE UPPER(CONCAT('%', CAST(:subtype AS string), '%'))) " +
            "AND (:city IS NULL OR UPPER(CAST(obj.address.city AS string)) LIKE UPPER(CONCAT('%', CAST(:city AS string), '%'))) " +
            "AND (:state IS NULL OR UPPER(CAST(obj.address.state AS string)) LIKE UPPER(CONCAT('%', CAST(:state AS string), '%'))) " +
            "AND (:neighborhood IS NULL OR UPPER(CAST(obj.address.neighborhood AS string)) LIKE UPPER(CONCAT('%', CAST(:neighborhood AS string), '%'))) " +
            "AND (:minPrice IS NULL OR obj.salePrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR obj.salePrice <= :maxPrice) " +
            "AND (:minArea IS NULL OR obj.totalArea >= :minArea) " +
            "AND (:maxArea IS NULL OR obj.totalArea <= :maxArea) " +
            "AND (:bedrooms IS NULL OR obj.bedrooms = :bedrooms) " +
            "AND (:suites IS NULL OR obj.suites = :suites) " +
            "AND (:garage IS NULL OR obj.garage = :garage) " +
            "AND (:zipCode IS NULL OR UPPER(CAST(obj.address.zipCode AS string)) LIKE UPPER(CONCAT('%', CAST(:zipCode AS string), '%'))) " +
            "AND (:option IS NULL OR obj.option = :option OR obj.option = 'SALE_RENT')"
    )
    Page<Immobile> search(
            @Param("title") String title,
            @Param("category") ImmobileCategory category,
            @Param("subtype") String subtype,
            @Param("city") String city,
            @Param("state") String state,
            @Param("neighborhood") String neighborhood,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minArea") Double minArea,
            @Param("maxArea") Double maxArea,
            @Param("bedrooms") Integer bedrooms,
            @Param("suites") Integer suites,
            @Param("garage") Integer garage,
            @Param("zipCode") String zipCode,
            @Param("option") OptionImmobile option,
            Pageable pageable);
}
