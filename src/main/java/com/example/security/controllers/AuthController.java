package com.example.security.controllers;

import com.example.security.dtos.UserDTO;
import com.example.security.exceptions.DuplicateException;
import com.example.security.filters.AuthenticationResponse;
import com.example.security.models.Role;
import com.example.security.models.User;
import com.example.security.payload.AuthenticationRequest;
import com.example.security.repositories.RoleRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.sevices.imp.MyUserDetailsService;
import com.example.security.ultis.Convert;
import com.example.security.ultis.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InvalidObjectException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),authenticationRequest.getPassword()
            ));
        }catch(BadCredentialsException ex){
            throw new DuplicateException("Incorrect username or password");
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        List<String> roles = new ArrayList<>();
        Set<Role> roleSet = user.getRoless();
        if(roleSet.size() > 0){
            roleSet.forEach(item -> roles.add(item.getRoleName()));
        }
        return ResponseEntity.ok(new AuthenticationResponse(jwt,user.getIdUser().intValue(),user.getUsername(),roles));
    }
    @PostMapping("/signup-user")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> signupUser(@RequestBody UserDTO userDTO) throws InvalidObjectException {
        User oldUser = userRepository.findByUsername(userDTO.getUsername());
        if(oldUser != null) {
            throw new DuplicateException("Username has already exists");
        }
        User user = Convert.fromUserDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //gán role member cho user mới lập
        Role role = roleRepository.findByRoleName("ROLE_USER");
        user.setRoless(Set.of(role));
        User newUser = userRepository.save(user);
        Set<User> users = role.getUserss();
        users.add(user);
        role.setUserss(users);
        roleRepository.save(role);
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(newUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, newUser.getIdUser().intValue(), newUser.getUsername(), List.of(role.getRoleName())));
    }
    @PostMapping("/signup-admin")
    public ResponseEntity<?> signupAdmin(@RequestBody UserDTO userDTO) throws InvalidObjectException {
        User oldUser = userRepository.findByUsername(userDTO.getUsername());
        if(oldUser != null) {
            throw new DuplicateException("Username has already exists");
        }
        User user = Convert.fromUserDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //gán role member cho user mới lập
        Role role = roleRepository.findByRoleName("ROLE_ADMIN");
        user.setRoless(Set.of(role));
        User newUser = userRepository.save(user);
        Set<User> users = role.getUserss();
        users.add(user);
        role.setUserss(users);
        roleRepository.save(role);
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(newUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, newUser.getIdUser().intValue(), newUser.getUsername(), List.of(role.getRoleName())));
    }
}
