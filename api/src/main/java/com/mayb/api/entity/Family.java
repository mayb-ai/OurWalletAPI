package com.mayb.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    // Código curto para compartilhar (Ex: MAYB-8821)
    @Column(unique = true, nullable = false, length = 10)
    private String inviteCode;

    // Relacionamento Inverso: Uma família tem VÁRIOS usuários
    // O JsonIgnore evita "loop infinito" quando o Swagger tenta mostrar os dados
    @JsonIgnore
    @OneToMany(mappedBy = "family")
    private List<User> members;

    // --- MÁGICA DO SPRING (Acontecimentos antes de salvar) ---

    @PrePersist
    public void generateInviteCode() {
        // Se ainda não tem código, gera um aleatório
        if (this.inviteCode == null) {
            // Pega os 4 primeiros caracteres do UUID e deixa maiúsculo
            String code = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            // Adiciona um prefixo (opcional)
            this.inviteCode = "FAM-" + code;
        }
    }
}
