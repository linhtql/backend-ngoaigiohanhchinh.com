package com.nekol.model.converter;

import com.nekol.model.entity.CategoryEntity;
import com.nekol.model.payload.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO toDto(CategoryEntity entity) {
        CategoryDTO dto = modelMapper.map(entity, CategoryDTO.class);

        return dto;
    }

    public CategoryEntity toEntity(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setParentId(dto.getParentId());
        entity.setName(dto.getName());

        return entity;
    }

    public CategoryEntity toEntity(CategoryEntity entity, CategoryDTO dto) {
        entity.setParentId(dto.getParentId());
        entity.setName(dto.getName());

        return entity;
    }
}
