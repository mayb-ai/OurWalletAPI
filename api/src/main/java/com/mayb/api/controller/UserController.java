package com.mayb.api.controller;

import com.mayb.api.dto.UserCreateRequest;
import com.mayb.api.entity.User;
import com.mayb.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public User createUser(@RequestBody @Valid UserCreateRequest data){
        User newUser = new User();
        newUser.setFullName(data.fullName());
        newUser.setUsername(data.username());
        newUser.setDisplayName(data.displayName());
        newUser.setEmail(data.email());
        newUser.setCpf(data.cpf());
        newUser.setPassword(data.password());
        newUser.setPhone(data.phone());
        newUser.setInviteCode(data.inviteCode());
        newUser.setRole("USER");

        return userService.createUser(newUser);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/{id}/join-family")
    public User joinFamily(@PathVariable UUID id, @RequestParam String code) {
        return userService.joinFamily(id, code);
    }
}
