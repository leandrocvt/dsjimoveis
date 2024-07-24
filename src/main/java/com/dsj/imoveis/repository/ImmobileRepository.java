package com.dsj.imoveis.repository;

import com.dsj.imoveis.lib.entities.Immobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmobileRepository extends JpaRepository<Immobile, Long> {
}
