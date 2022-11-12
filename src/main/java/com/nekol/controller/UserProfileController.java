package com.nekol.controller;

import com.nekol.model.payload.PageResponseProfile;
import com.nekol.model.payload.ResponseObject;
import com.nekol.model.payload.UserProfileDTO;
import com.nekol.service.IUserProfileService;
import com.nekol.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class UserProfileController {

    @Autowired
    private IUserProfileService userProfileService;

    @GetMapping("/profile/{username}")
    public ResponseEntity<ResponseObject> getUserProfile(@PathVariable(name = "username") String username) {
        UserProfileDTO profileResponse = userProfileService.getProfileByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query profile successfully !", profileResponse));
    }

    @GetMapping("/profiles/role/{role_id}")
    public PageResponseProfile getAllProfileByRole(@PathVariable Long role_id,
                                                   @RequestParam(value = "pageNo", required = false,
                                                           defaultValue = AppConstants.PAGE_NUMBER) Integer pageNo,
                                                   @RequestParam(value = "pageSize", required = false,
                                                           defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                   @RequestParam(value = "sortBy", required = false,
                                                           defaultValue = AppConstants.SORT_BY) String sortBy,
                                                   @RequestParam(value = "sortDir", required = false,
                                                           defaultValue = AppConstants.SORT_DIRECTION) String dir) {
        return userProfileService.getAllByRole(role_id, pageNo, pageSize, sortBy, dir);
    }

    @PostMapping("/profile/avatar")
    public ResponseEntity<String> updateAvatar(
            @RequestPart ("image") MultipartFile multipartFile) throws IOException {

        String urlAvatarPhoto = userProfileService.setAvatarImg(multipartFile);
        return new ResponseEntity<>(urlAvatarPhoto, HttpStatus.OK);
    }

    @PostMapping("/profile/coverPhoto")
    public ResponseEntity<String> updateCoverPhoto(
            @RequestPart ("image") MultipartFile multipartFile) throws IOException {

        String urlCoverPhoto = userProfileService.setCoverImg(multipartFile);
        return new ResponseEntity<>(urlCoverPhoto, HttpStatus.OK);
    }
}
