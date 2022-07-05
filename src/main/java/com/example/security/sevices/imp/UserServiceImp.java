package com.example.security.sevices.imp;

import com.example.security.dtos.UserDTO;
import com.example.security.exceptions.DuplicateException;
import com.example.security.exceptions.NotFoundException;
import com.example.security.models.Role;
import com.example.security.models.User;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.sevices.IUserService;
import com.example.security.ultis.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Set<User> getAllUser() {
        Set<User> users = new HashSet<>(userRepository.findAll());
        if(users.size()==0)
            throw new NotFoundException("Khong ton tai user nao");
        return users;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if(user != null)
            throw new DuplicateException("Duplicate User");
        User user1 = Convert.fromUserDTOToUser(userDTO);
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        Set<Role> userRole = new HashSet<>();
        for(Role role : roles)
            if(role.getRoleName().compareTo("ROLE_USER")==0)
                userRole.add(role);
        user1.setRoless(userRole);

        User newUser = userRepository.save(user1);
        return newUser;
    }

    @Override
    public User updateUser(UserDTO userDTO, Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new NotFoundException("Khong co id nay");
        User newUser = Convert.fromUserDTOToUser(userDTO);
        newUser.setIdUser(user.get().getIdUser());
        return userRepository.save(newUser);
    }

    @Override
    public User deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new NotFoundException("Khong co id nay");
        userRepository.delete(user.get());
        return user.get();
    }
}
