package com.dsj.imoveis.mapper;

import com.dsj.imoveis.lib.dto.UserDTO;
import com.dsj.imoveis.lib.entities.User;

public interface UserMapper {

    UserDTO mapUserDTO(User user);
}
