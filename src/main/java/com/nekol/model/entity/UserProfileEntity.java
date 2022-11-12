package com.nekol.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_profile")
@Data
public class UserProfileEntity extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String gender;

    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name = "avatar_photo")
    private String avatarPhoto;

    @Column(name = "cover_photo")
    private String coverPhoto;

    @Column
    private String about;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "userProfile")
    private Set<PostEntity> postEntities = new HashSet<>();

    @OneToMany(mappedBy = "userProfile")
    private Set<CommentEntity> commentEntities = new HashSet<>();
}
