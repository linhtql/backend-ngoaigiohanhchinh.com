package com.nekol.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Data
public class CommentEntity extends BaseEntity {

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfileEntity userProfile;


}
