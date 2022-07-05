package com.example.security.controllers;

import com.example.security.bases.BaseController;
import com.example.security.dtos.RoleDTO;
import com.example.security.models.Role;
import com.example.security.sevices.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController<Role> {
    @Autowired
    private IRoleService iRoleService;

    @GetMapping
    public ResponseEntity<?> getAllRole(){
        return this.resSetSuccess(iRoleService.getAllRole());
    }
    @GetMapping("/{id_role}")
    public ResponseEntity<?> getRoleById(@PathVariable(name = "id_role")Integer id_role){
        return this.resSuccess(iRoleService.getRoleById(id_role));
    }
    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleDTO roleDTO){
        return this.resSuccess(iRoleService.createRole(roleDTO));
    }
    @PatchMapping("/{id_role}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO,
                                        @PathVariable(name = "id_role")Integer id_role){
        return this.resSuccess(iRoleService.updateRole(roleDTO,id_role));
    }
    @DeleteMapping("/{id_role}")
    public ResponseEntity<?> deleteRole(@PathVariable(name = "id_role")Integer id_role){
        return this.resSuccess(iRoleService.deleteRole(id_role));
    }
}
