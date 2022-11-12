package com.nekol.service;

import com.nekol.model.payload.PageResponseProfile;
import com.nekol.model.payload.UserProfileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserProfileService {
    UserProfileDTO getProfileByUsername(String username);
    UserProfileDTO setInfoUser(UserProfileDTO userProfileDTO);
    PageResponseProfile getAllByRole(Long role_id, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
    String setAvatarImg(MultipartFile multipartFile) throws IOException;
    String setCoverImg(MultipartFile multipartFile) throws IOException;
}
