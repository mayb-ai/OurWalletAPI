package com.mayb.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mayb.api.entity.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Verificações de existência (para o cadastro não quebrar)
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByCpf(String cpf);

    // Para o Login Futuro (Achar usuário por qualquer um dos meios)
    // Isso busca: Onde (email = X) OU (username = X) OU (cpf = X)
    Optional<User> findByEmailOrUsernameOrCpf(String email, String username, String cpf);

    Optional<User> findByEmail(String email);
}