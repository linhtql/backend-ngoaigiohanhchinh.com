package com.nekol.model.payload;

import lombok.Data;

@Data
public class RegisterDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}
