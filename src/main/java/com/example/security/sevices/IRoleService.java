package com.example.security.sevices;

import com.example.security.dtos.RoleDTO;
import com.example.security.models.Role;

import java.util.Set;

public interface IRoleService {
    Set<Role> getAllRole();
    Role getRoleById(Integer id_role);
    Role createRole(RoleDTO roleDTO);
    Role updateRole(RoleDTO roleDTO,Integer id_role);
    Role deleteRole(Integer id_role);
}
