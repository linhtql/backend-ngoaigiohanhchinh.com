package com.nekol.service.impl;

import com.nekol.model.payload.RoleDTO;
import com.nekol.repository.RoleRepository;
import com.nekol.repository.UserRepository;
import com.nekol.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        return null;
    }

    @Override
    public List<RoleDTO> findAllRole() {
        return null;
    }

    @Override
    public RoleDTO findById(Integer id) {
        return null;
    }

    @Override
    public void deleteRole(Integer id) {

    }
}
