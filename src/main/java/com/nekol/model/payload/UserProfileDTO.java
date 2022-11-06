package com.nekol.model.payload;

import lombok.Data;

import java.util.Date;

@Data
public class UserProfileDTO {
    private Long id;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String gender;
    private Date dateOfBirth;
    private String avatarPhoto;
    private String coverPhoto;

    private String about;
}
