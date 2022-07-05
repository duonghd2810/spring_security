package com.example.security.controllers;

import com.example.security.bases.BaseController;
import com.example.security.dtos.UserDTO;
import com.example.security.models.User;
import com.example.security.sevices.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController<User> {
    @Autowired
    private IUserService iUserService;

    @GetMapping()
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.status(200).body(iUserService.getAllUser());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(200).body(iUserService.createUser(userDTO));
    }
    @DeleteMapping("/{id_user}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id_user")Integer idUser){
        return this.resSuccess(iUserService.deleteUser(idUser));
    }
}
