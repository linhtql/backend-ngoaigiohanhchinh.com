package com.nekol.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Data
public class CategoryEntity extends BaseEntity{

    @Column
    private String name;

    @Column
    private Long parent_id;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL)
    Set<PostEntity> posts = new HashSet<>();
}
