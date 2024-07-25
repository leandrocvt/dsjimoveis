package com.dsj.imoveis.service;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImmobileService {

    ImmobileDTO save(final ImmobileDTO dto);

    ImmobileDTO findById(Long id);

    Page<ImmobileMinDTO> findAll(String title,
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
