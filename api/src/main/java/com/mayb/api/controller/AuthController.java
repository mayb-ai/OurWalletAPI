package com.mayb.api.controller;

import com.mayb.api.dto.LoginRequest;
import com.mayb.api.dto.LoginResponse;
import com.mayb.api.entity.User;
import com.mayb.api.repository.UserRepository;
import com.mayb.api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest body){
        // 1. Busca o usuário no banco pelo E-mail
        User user = userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // 2. Verifica se a senha bate
        // O .matches(senhaAberta, senhaCriptografada) faz a mágica matemática
        if (passwordEncoder.matches(body.password(), user.getPassword())) {

            // 3. Se a senha confere, gera o token
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(token));
        }

        // 4. Se a senha não bater
        return ResponseEntity.badRequest().build();
    }

}
