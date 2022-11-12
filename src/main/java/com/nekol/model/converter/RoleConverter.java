package com.nekol.model.converter;

import com.nekol.model.entity.RoleEntity;
import com.nekol.model.payload.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO toDto(RoleEntity entity) {
        RoleDTO dto = new RoleDTO();
        dto.setName(entity.getName());
        return dto;
    }

    public RoleEntity toEntity(RoleDTO dto) {
        RoleEntity entity = new RoleEntity();
        entity.setName(dto.getName());
        return entity;
    }

    public RoleEntity toEntity(RoleDTO dto, RoleEntity entity) {
        entity.setName(dto.getName());

        return entity;
    }
}
