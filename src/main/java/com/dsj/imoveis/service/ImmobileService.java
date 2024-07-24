package com.dsj.imoveis.service;

import com.dsj.imoveis.lib.dto.ImmobileDTO;

public interface ImmobileService {

    ImmobileDTO save(final ImmobileDTO dto);

    ImmobileDTO findById(Long id);

}
