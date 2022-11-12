package com.nekol.repository;

import com.nekol.model.entity.UserEntity;
import com.nekol.model.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    Optional<UserProfileEntity> findUserProfileEntityByUser(UserEntity userEntity);
}
