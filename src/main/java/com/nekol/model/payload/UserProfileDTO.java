package com.nekol.model.payload;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class UserProfileDTO {
    private Integer id;
    private String emailByUser;
    private String usernameByUser;
    private Set<RoleDTO> roles;
    private String firstName;
    private String lastName;
    private String fullName;
    private String gender;
    private Date dateOfBirth;
    private String avatarPhoto;
    private String coverPhoto;
    private String about;
}
