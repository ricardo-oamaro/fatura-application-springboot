package br.com.backend.fatura.application.model.user;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
