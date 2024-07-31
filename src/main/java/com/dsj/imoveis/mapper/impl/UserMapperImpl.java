package com.dsj.imoveis.mapper.impl;

import com.dsj.imoveis.lib.dto.UserDTO;
import com.dsj.imoveis.lib.entities.Role;
import com.dsj.imoveis.lib.entities.User;
import com.dsj.imoveis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO mapUserDTO(User user) {
        List<String> roles = user.getRoles().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

}
