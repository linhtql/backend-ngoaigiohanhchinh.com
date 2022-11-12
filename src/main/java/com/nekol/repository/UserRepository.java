package com.nekol.repository;

import com.nekol.model.entity.RoleEntity;
import com.nekol.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findUserEntitiesByRoles(RoleEntity role);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
