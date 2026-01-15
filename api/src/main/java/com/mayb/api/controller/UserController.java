package com.mayb.api.controller;

import com.mayb.api.entity.User;
import com.mayb.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
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
