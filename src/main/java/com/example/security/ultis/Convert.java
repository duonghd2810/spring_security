package com.example.security.ultis;

import com.example.security.dtos.RoleDTO;
import com.example.security.dtos.UserDTO;
import com.example.security.models.Role;
import com.example.security.models.User;

public class Convert {
    public static Role fromRoleDTOToRole(RoleDTO roleDTO){
        Role role = new Role();
        if(roleDTO.getRoleName()!=null)
            role.setRoleName(roleDTO.getRoleName());
        return role;
    }
    public static User fromUserDTOToUser(UserDTO userDTO){
        User user = new User();
        if(userDTO.getName()!=null)
            user.setName(userDTO.getName());
        if(userDTO.getPhone()!=null)
            user.setPhone(userDTO.getPhone());
        if(userDTO.getUsername()!=null)
            user.setUsername(userDTO.getUsername());
        if(userDTO.getPassword()!=null)
            user.setPassword(userDTO.getPassword());
        return user;
    }
}
