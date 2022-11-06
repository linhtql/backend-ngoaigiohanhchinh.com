package com.nekol.service.impl;

import com.nekol.repository.UserProfileRepository;
import com.nekol.repository.UserRepository;
import com.nekol.service.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements IUserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

}
