package com.nekol.service;

import com.nekol.model.payload.RegisterDTO;

public interface IAuthService {
    void createUser(RegisterDTO registerDTO);
}
