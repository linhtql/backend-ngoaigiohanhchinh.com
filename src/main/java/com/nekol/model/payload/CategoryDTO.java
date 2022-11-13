package com.nekol.model.payload;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;
    private String name;
    private Long parentId;
}
