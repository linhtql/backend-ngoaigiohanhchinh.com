package com.nekol.model.converter;

import com.nekol.model.entity.RoleEntity;
import com.nekol.model.entity.UserEntity;
import com.nekol.model.entity.UserProfileEntity;
import com.nekol.model.payload.RoleDTO;
import com.nekol.model.payload.UserProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserProfileConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UserProfileDTO toDto(UserProfileEntity entity) {
        UserProfileDTO dto = modelMapper.map(entity, UserProfileDTO.class);
        UserEntity userEntity = entity.getUser();
        dto.setFullName(entity.getLastName() + " " + entity.getFirstName());
        dto.setEmailByUser(userEntity.getEmail());
        dto.setUsernameByUser(userEntity.getUsername());

        Set<RoleDTO> roleDTOS = new HashSet<>();
        RoleConverter roleConverter = new RoleConverter();
        for (RoleEntity roleEntity : userEntity.getRoles()) {
            RoleDTO roleDTO = roleConverter.toDto(roleEntity);
            roleDTOS.add(roleDTO);
        }
        dto.setRoles(roleDTOS);
        return dto;
    }
    public UserProfileEntity toEntity(UserProfileEntity entity, UserProfileDTO dto) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setGender(dto.getGender());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setAbout(dto.getAbout());

        return entity;
    }

}
