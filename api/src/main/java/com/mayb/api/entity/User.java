package com.mayb.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Nome Completo (Nome + Sobrenome)
    @Column(nullable = false)
    private String fullName;

    // Nome de usuário (único, para login)
    @Column(unique = true, nullable = false)
    private String username;

    // Como quer que apareça (Apelido)
    private String displayName;

    // CPF (único, essencial para pagamentos e login)
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone; // Para verificação 2FA

    @Column(nullable = false)
    private String password;

    private String role; //tipo de usuário: ADMIN, USER comum como dependentes, etc.

    @ManyToOne
    @JoinColumn(name = "family_id", nullable = true)
    private Family family;

    @Transient
    private String inviteCode;
}
