package br.com.backend.fatura.application.model.user;

public record RegisterDTO(String email, String password, UserRole role) {
}
