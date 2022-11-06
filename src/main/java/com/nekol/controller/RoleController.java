package com.nekol.controller;

import com.nekol.model.entity.RoleEntity;
import com.nekol.model.payload.ResponseObject;
import com.nekol.model.payload.RoleDTO;
import com.nekol.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/roles")
    public List<RoleDTO> getAllRoles() {
        List<RoleDTO> listRoles = roleService.findAllRole();
        return listRoles;
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<ResponseObject> getById(@PathVariable(name = "id") Long id) {
        RoleDTO roleDTO = roleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Query role successfully !", roleDTO));
    }

    @PostMapping("/role")
    public ResponseEntity<ResponseObject> createRole(@RequestBody RoleDTO roleDTO) {
      RoleDTO roleResponse = roleService.saveRole(roleDTO);
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Create role successfully !", roleResponse));
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<ResponseObject> updateRole(@PathVariable(name = "id") Long id, @RequestBody RoleDTO roleDTO ) {
        roleDTO.setId(id);
        RoleDTO roleResponse = roleService.saveRole(roleDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Update role successfully !", roleResponse));

    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<ResponseObject> deleteById(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete role successfully", ""));
    }
}
