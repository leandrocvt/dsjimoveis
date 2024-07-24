package com.dsj.imoveis.mapper;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.entities.Immobile;

public interface ImmobileMapper {

    Immobile mapImmobile(ImmobileDTO dto);
    ImmobileDTO mapImmobileDTO(Immobile entity);
}
