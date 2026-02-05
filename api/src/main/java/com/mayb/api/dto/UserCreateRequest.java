package com.mayb.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserCreateRequest(
        @NotBlank(message = "O nome não pode estar em branco")
        String fullName,

        @NotBlank(message = "Username é obrigatório")
        String username,

        String displayName,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "Informe um e-mail válido.")
        String email,

        @NotBlank(message = "CPF é obrigatório")
        @CPF(message = "CPF inválido")
        @Size(min = 11, max = 14, message = "CPF deve ter apenas números ou formato padrão")
        String cpf,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password,

        @NotBlank(message = "Telefone é obrigatório.")
        String phone,

        String inviteCode) {
}
