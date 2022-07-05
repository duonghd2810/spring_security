package com.example.security.sevices;

import com.example.security.dtos.UserDTO;
import com.example.security.models.User;

import java.util.Set;

public interface IUserService {
    Set<User> getAllUser();
    User createUser(UserDTO userDTO);
    User updateUser(UserDTO userDTO,Integer id);
    User deleteUser(Integer id);
}
