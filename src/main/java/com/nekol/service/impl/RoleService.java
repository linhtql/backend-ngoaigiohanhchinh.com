package com.nekol.service.impl;

import com.nekol.exception.APIException;
import com.nekol.exception.ResourceNotFoundException;
import com.nekol.model.converter.RoleConverter;
import com.nekol.model.entity.RoleEntity;
import com.nekol.model.entity.UserEntity;
import com.nekol.model.payload.RoleDTO;
import com.nekol.repository.RoleRepository;
import com.nekol.repository.UserRepository;
import com.nekol.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleConverter converter;

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        for (RoleEntity roleEntity : roleEntities) {
            if (roleEntity.getName().compareTo(roleDTO.getName()) == 0) {
                throw new APIException(HttpStatus.BAD_REQUEST, "Role already exists");
            }
        }

        RoleEntity roleEntity;
        if (roleDTO.getId() == null) {
            // create
            roleEntity = converter.toEntity(roleDTO);
        } else {
            // update
            RoleEntity oldRole = roleRepository.findById(roleDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleDTO.getId()));
            roleEntity = converter.toEntity(roleDTO, oldRole);
        }

        RoleEntity newRole = roleRepository.save(roleEntity);
        return converter.toDto(newRole);


    }

    @Override
    public List<RoleDTO> findAllRole() {
        List<RoleDTO> listRoles = roleRepository.findAll()
                .stream().map(role -> converter.toDto(role)).collect(Collectors.toList());
        return listRoles;
    }

    @Override
    public RoleDTO findById(Long id) {
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return converter.toDto(roleEntity);
    }

    @Override
    public void deleteRole(Long id) {
        RoleEntity roleRemove = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        List<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            Set<RoleEntity> roleEntities = userEntity.getRoles();

            for (RoleEntity roleEntity : roleEntities) {
                if (roleEntity.getId() == id) {
                    throw new APIException(HttpStatus.BAD_REQUEST,
                            "This role can't be deleted because there are users that apply!");
                }
            }
        }
        roleRepository.delete(roleRemove);
    }
}
