package com.mayb.api.service;

import com.mayb.api.entity.Family;
import com.mayb.api.entity.User;
import com.mayb.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mayb.api.repository.FamilyRepository;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired // <--- Injetamos o repositório da Família também!
    private FamilyRepository familyRepository;

    public User createUser(User user){
        // 1. Limpeza de dados (Remove espaços extras)
        sanitizeUser(user);

        // 2. Validações de Formato (Regex)
        validateFormat(user);

        // 3. Validação de Duplicidade (Regra de Negócio)
        validateUniqueness(user);

        // Se o usuário mandou um código de convite...
        if (user.getInviteCode() != null && !user.getInviteCode().isEmpty()) {

            // Buscamos a família pelo código
            Family family = familyRepository.findByInviteCode(user.getInviteCode())
                    .orElseThrow(() -> new RuntimeException("Código de convite inválido!"));

            // Se achou, vincula o usuário a ela
            user.setFamily(family);
        }else {
            // Se não mandou código, tudo bem!
            // A família vai ficar NULL no banco. Ele é um usuário "solteiro" por enquanto.
            user.setFamily(null);
        }

        // Se ele NÃO mandou código, ele fica sem família (family = null)
        // e provavelmente vai criar uma nova depois.

        // Se passou por tudo, salva!
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    // --- MÉTODOS AUXILIARES (Private) ---

    private void sanitizeUser(User user) {
        if (user.getFullName() != null)
            user.setFullName(user.getFullName().trim());
        if (user.getUsername() != null)
            user.setUsername(user.getUsername().trim().toLowerCase()); // Username sempre minúsculo
        if (user.getEmail() != null)
            user.setEmail(user.getEmail().trim().toLowerCase());

        // Remove pontos e traços do CPF para salvar apenas números
        if (user.getCpf() != null) user.setCpf(user.getCpf().replaceAll("\\D", ""));
    }

    private void validateFormat(User user) {
        // Valida CPF (apenas verifica se tem 11 dígitos por enquanto)
        if (user.getCpf() == null || user.getCpf().length() != 11) {
            throw new RuntimeException("Erro: CPF inválido. Deve conter 11 dígitos.");
        }

        // Valida E-mail com Regex simples
        if (user.getEmail() == null || !user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new RuntimeException("Erro: E-mail inválido.");
        }

        // Valida Senha (Mínimo 6 caracteres)
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new RuntimeException("Erro: A senha deve ter no mínimo 6 caracteres.");
        }
    }

    private void validateUniqueness(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Erro: Já existe um usuário com este e-mail.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Erro: Este nome de usuário já está em uso.");
        }
        if (userRepository.existsByCpf(user.getCpf())) {
            throw new RuntimeException("Erro: Já existe um cadastro com este CPF.");
        }
    }


    // Metodo para usuário existente entrar na família
    public User joinFamily(UUID userId, String inviteCode) {

        // 1. Busca o usuário que já existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Busca a família pelo código (FAM-XXXX)
        Family family = familyRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Código de convite inválido!"));

        // 3. Atualiza o vínculo
        user.setFamily(family);

        // 4. Salva a alteração
        return userRepository.save(user);
    }
}
