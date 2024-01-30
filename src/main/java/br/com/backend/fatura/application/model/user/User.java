package br.com.backend.fatura.application.model.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private ObjectId id;

    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome não pode ser vazio")
    @Size(min = 3, max = 30, message = "O campo deve ter entre 3 e 30 caracteres")
    private String name;

    @NotNull(message = "O email não pode ser nulo")
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Por favor, forneça um endereço de e-mail válido")
    private String email;

    @NotNull(message = "A senha não pode ser nulo")
    @NotBlank(message = "A senha não pode ser vazio")
    private String password;

    private UserRole role;

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
