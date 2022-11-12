package com.nekol.service.impl;

import com.nekol.exception.APIException;
import com.nekol.model.entity.RoleEntity;
import com.nekol.model.entity.UserEntity;
import com.nekol.model.entity.UserProfileEntity;
import com.nekol.model.payload.RegisterDTO;
import com.nekol.repository.RoleRepository;
import com.nekol.repository.UserProfileRepository;
import com.nekol.repository.UserRepository;
import com.nekol.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(RegisterDTO registerDTO) {
        UserEntity user = setField(registerDTO);
        RoleEntity role = roleRepository.findByName("ROLE_USER").get();

        Set<RoleEntity> roleEntities = new HashSet<>();
        roleEntities.add(role);
        user.setRoles(roleEntities);


        userRepository.save(user);

    }

    private UserEntity setField(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already taken");
        }
        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setCreatedDate(new Date());


        UserProfileEntity userProfile = new UserProfileEntity();
        userProfile.setUser(user);
        userProfile.setFirstName(registerDTO.getFirstName());
        userProfile.setLastName(registerDTO.getLastName());
        userProfile.setAvatarPhoto("https://res.cloudinary.com/dxultkptn/image/upload/v1668182670/d5gkvss00t2cyeovgcnc.jpg");
        userProfile.setCoverPhoto("https://res.cloudinary.com/dxultkptn/image/upload/v1668237948/ou3htd6y3ygbxjsurvmd.png");

        userProfileRepository.save(userProfile);

        return user;

    }
}
