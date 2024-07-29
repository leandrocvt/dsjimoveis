package com.dsj.imoveis.service;

import com.dsj.imoveis.lib.dto.ImmobileDTO;
import com.dsj.imoveis.lib.dto.ImmobileMinDTO;
import com.dsj.imoveis.lib.enums.ImmobileCategory;
import com.dsj.imoveis.lib.enums.OptionImmobile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImmobileService {

    ImmobileDTO save(final ImmobileDTO dto);

    ImmobileDTO findById(final Long id);

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
                                 OptionImmobile option,
                                 Pageable pageable);

    ImmobileDTO update(final Long id, final ImmobileDTO dto);

    void delete(final Long id);

}
