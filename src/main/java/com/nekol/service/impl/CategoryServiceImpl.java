package com.nekol.service.impl;

import com.nekol.exception.APIException;
import com.nekol.exception.ResourceNotFoundException;
import com.nekol.model.converter.CategoryConverter;
import com.nekol.model.entity.CategoryEntity;
import com.nekol.model.entity.PostEntity;
import com.nekol.model.payload.CategoryDTO;
import com.nekol.repository.CategoryRepository;
import com.nekol.repository.PostRepository;
import com.nekol.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter converter;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CategoryDTO findById(Long id) {

        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        return converter.toDto(entity);
    }

    @Override
    public List<CategoryDTO> getALl() {

        List<CategoryEntity> entityList = categoryRepository.findAll();
        List<CategoryDTO> response = new ArrayList<>();

        for (CategoryEntity entity : entityList) {
            response.add(converter.toDto(entity));
        }
        return response;
    }

    @Override
    public void deleteById(Long id) {
        CategoryEntity categoryById = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        List<PostEntity> postList = postRepository.findAll();

        for (PostEntity post : postList) {
            Set<CategoryEntity> categoriesInPost = post.getCategories();

            for (CategoryEntity category : categoriesInPost) {
                if (category.getId() == id) {
                    throw new APIException(HttpStatus.BAD_REQUEST, "This category can't be deleted because there are Post that apply");
                }
            }
        }


        categoryRepository.delete(categoryById);


    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();

        for (CategoryEntity categoryEntity : categoryEntities) {
            if (categoryEntity.getName().compareTo(categoryDTO.getName()) == 0) {
                throw new APIException(HttpStatus.BAD_REQUEST, "Category already exists");
            }
        }
        CategoryEntity categoryEntity;
        if (categoryDTO.getId() == null) {
            // create
            categoryEntity = converter.toEntity(categoryDTO);
        } else {
            // update
            CategoryEntity oldCategory = categoryRepository.findById(categoryDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryDTO.getId()));
            categoryEntity = converter.toEntity(oldCategory, categoryDTO);
        }

        CategoryEntity newCategory = categoryRepository.save(categoryEntity);
        CategoryDTO responseCategory = converter.toDto(newCategory);
        return responseCategory;
    }
}
