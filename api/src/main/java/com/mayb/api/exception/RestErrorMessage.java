package com.mayb.api.exception;

public record RestErrorMessage(Integer status /*(400,500 etc)*/, String message /* Ex: "Usuario não encontrado"*/ ) {
}
