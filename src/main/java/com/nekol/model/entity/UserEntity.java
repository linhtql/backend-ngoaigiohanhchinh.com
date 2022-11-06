package com.nekol.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class UserEntity extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 70)
    private String email;

    private boolean enable;

    @OneToOne(mappedBy = "user")
    private UserProfileEntity userProfileEntity;


    public void addRole(RoleEntity role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(RoleEntity role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "userProfile")
    private Set<PostEntity> postEntities = new HashSet<>();

    @OneToMany(mappedBy = "userProfile")
    private Set<CommentEntity> commentEntities = new HashSet<>();


}
