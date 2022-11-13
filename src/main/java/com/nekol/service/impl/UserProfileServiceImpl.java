package com.nekol.service.impl;

import com.nekol.exception.ResourceNotFoundException;
import com.nekol.model.converter.UserProfileConverter;
import com.nekol.model.entity.RoleEntity;
import com.nekol.model.entity.UserEntity;
import com.nekol.model.entity.UserProfileEntity;
import com.nekol.model.payload.CustomUser;
import com.nekol.model.payload.PageResponseProfile;
import com.nekol.model.payload.UserProfileDTO;
import com.nekol.repository.RoleRepository;
import com.nekol.repository.UserProfileRepository;
import com.nekol.repository.UserRepository;
import com.nekol.service.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileConverter converter;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FileStorageServiceImpl storageService;

    @Override
    public UserProfileDTO getProfileByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "id", "username"));

        UserProfileEntity userProfile = userProfileRepository.findUserProfileEntityByUser(userEntity).get();
        UserProfileDTO profileResponse = converter.toDto(userProfile);

        return profileResponse;
    }

    @Override
    public UserProfileDTO setInfoUser(UserProfileDTO userProfileDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long userId = customUser.getUserId();

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        UserProfileEntity oldProfile = userProfileRepository.findUserProfileEntityByUser(userEntity).get();

        // logic

        UserProfileEntity newProfile = userProfileRepository.save(converter.toEntity(oldProfile, userProfileDTO));
        UserProfileDTO responseDto = converter.toDto(newProfile);

        return responseDto;
    }

    @Override
    public PageResponseProfile getAllByRole(Long role_id, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        RoleEntity roleEntity = roleRepository.findById(role_id).orElseThrow(() -> new ResourceNotFoundException("Role", "name", role_id));
        List<UserEntity> userEntityList = userRepository.findUserEntitiesByRoles(roleEntity);

        List<UserProfileEntity> profileEntityList = userEntityList.stream().map(UserEntity::getUserProfile).collect(Collectors.toList());

        Sort sortOj = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortOj);

        Page<UserProfileEntity> page = new PageImpl<>(profileEntityList, pageable, profileEntityList.size());
        PageResponseProfile pageResponse = pagingProfile(page);

        return pageResponse;
    }

    @Override
    public String setAvatarImg(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long userId = customUser.getUserId();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfileEntity userProfile = userProfileRepository.findUserProfileEntityByUser(userEntity).get();
        String urlAvatar = null;
        if (!file.isEmpty()) {
            urlAvatar = storageService.storageFile(file);
            userProfile.setAvatarPhoto(urlAvatar);
        } else {
            if (userProfile.getAvatarPhoto().isEmpty()) {
                urlAvatar = "https://res.cloudinary.com/dxultkptn/image/upload/v1668182670/d5gkvss00t2cyeovgcnc.jpg";
                userProfile.setAvatarPhoto(urlAvatar);
            }
        }
        UserProfileEntity newUserProfile = userProfileRepository.save(userProfile);
        return urlAvatar;
    }

    @Override
    public String setCoverImg(MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Long userId = customUser.getUserId();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfileEntity userProfile = userProfileRepository.findUserProfileEntityByUser(userEntity).get();

        String urlCover = null;
        if (!file.isEmpty()) {
            urlCover = storageService.storageFile(file);
            userProfile.setCoverPhoto(urlCover);
        } else {
            if (userProfile.getAvatarPhoto().isEmpty()) {
                urlCover = "https://res.cloudinary.com/dxultkptn/image/upload/v1668237948/ou3htd6y3ygbxjsurvmd.png";
                userProfile.setCoverPhoto(urlCover);
            } else {
            }
        }
        UserProfileEntity newUserProfile = userProfileRepository.save(userProfile);
        return urlCover;
    }

    private PageResponseProfile pagingProfile(Page<UserProfileEntity> profileEntities) {
        List<UserProfileEntity> profileEntityList = profileEntities.getContent();

        List<UserProfileDTO> contentList = new ArrayList<>();
        for (UserProfileEntity profileEntity : profileEntityList) {
            UserProfileDTO profileDTO = converter.toDto(profileEntity);
            contentList.add(profileDTO);
        }

        PageResponseProfile pageResponse = new PageResponseProfile();
        pageResponse.setPageNo(profileEntities.getNumber());
        pageResponse.setContent(contentList);
        pageResponse.setPageSize(profileEntities.getSize());
        pageResponse.setTotalPages(profileEntities.getTotalPages());
        pageResponse.setTotalElements((int) profileEntities.getTotalElements());
        pageResponse.setLast(profileEntities.isLast());

        return pageResponse;
    }
}

