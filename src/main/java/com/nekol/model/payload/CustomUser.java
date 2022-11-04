package com.nekol.model.payload;

import com.nekol.model.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

@Getter
public class CustomUser extends User {


    private final Long userId;
    private final Set<RoleEntity> roles;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, Set<RoleEntity> roles) {
        super(username, password, authorities);
        this.userId = userId;
        this.roles = roles;
    }

}
