package com.nekol.service;

import com.nekol.model.payload.RoleDTO;

import java.util.List;

public interface IRoleService {
    RoleDTO saveRole(RoleDTO roleDTO);

    List<RoleDTO> findAllRole();

    RoleDTO findById(Integer id);

    void deleteRole(Integer id);
}
