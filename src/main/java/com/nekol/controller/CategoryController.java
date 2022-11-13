package com.nekol.controller;

import com.nekol.model.payload.CategoryDTO;
import com.nekol.model.payload.ResponseObject;
import com.nekol.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable(name = "id") Long id) {
        CategoryDTO categoryResponse = categoryService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query Category successfully !", categoryResponse));
    }

    @GetMapping("/categories")
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> listResponse = categoryService.getALl();
        return listResponse;
    }

    @PostMapping("/category")
    public ResponseEntity<ResponseObject> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO categoryResponse = categoryService.save(categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create Category successfully !", categoryResponse));
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<ResponseObject> updateCategory(@PathVariable(name = "id") Long id, @RequestBody CategoryDTO categoryDTO) {

        categoryDTO.setId(id);
        CategoryDTO categoryResponse = categoryService.save(categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update Category successfully !", categoryResponse));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable(name = "id") Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete Category successfully", ""));
    }
}
