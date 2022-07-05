package com.example.security.sevices.imp;

import com.example.security.dtos.RoleDTO;
import com.example.security.exceptions.DuplicateException;
import com.example.security.exceptions.NotFoundException;
import com.example.security.models.Role;
import com.example.security.repositories.RoleRepository;
import com.example.security.sevices.IRoleService;
import com.example.security.ultis.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImp implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Set<Role> getAllRole() {
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        if(roles.size()==0)
            throw new NotFoundException("Khong ton tai danh sach nay");
        return roles;
    }

    @Override
    public Role getRoleById(Integer id_role) {
        Optional<Role> role = roleRepository.findById(id_role);
        if(role.isEmpty())
            throw new NotFoundException("Khong co role nay");
        return role.get();
    }

    @Override
    public Role createRole(RoleDTO roleDTO) {
        Role role = roleRepository.findByRoleName(roleDTO.getRoleName());
        if(role!=null)
            throw new DuplicateException("Duplicate role");
        Role role1 = Convert.fromRoleDTOToRole(roleDTO);
        Role role2 = roleRepository.save(role1);
        return role2;
    }

    @Override
    public Role updateRole(RoleDTO roleDTO, Integer id_role) {
        Optional<Role> role = roleRepository.findById(id_role);
        if(role.isEmpty())
            throw new NotFoundException("Khong co role nay");
        Role newRole = Convert.fromRoleDTOToRole(roleDTO);
        newRole.setIdRole(role.get().getIdRole());
        return roleRepository.save(newRole);
    }

    @Override
    public Role deleteRole(Integer id_role) {
        Optional<Role> role = roleRepository.findById(id_role);
        if(role.isEmpty())
            throw new NotFoundException("Khong co role nay");
        roleRepository.delete(role.get());
        return role.get();
    }
}
