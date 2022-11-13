package com.nekol.service;

import com.nekol.model.payload.CategoryDTO;

import java.util.List;

public interface ICategoryService {

    CategoryDTO findById(Long id);
    List<CategoryDTO> getALl();
    void deleteById(Long id);
    CategoryDTO save(CategoryDTO categoryDTO);
}
