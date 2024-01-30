package br.com.backend.fatura.application.controller;

import br.com.backend.fatura.application.model.user.AuthenticationDTO;
import br.com.backend.fatura.application.model.user.LoginResponseDTO;
import br.com.backend.fatura.application.model.user.User;
import br.com.backend.fatura.application.repo.UserRepository;
import br.com.backend.fatura.application.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User data){
        if(this.userRepository.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().body("Bad request.");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getName(), data.getEmail(), encryptedPassword, data.getRole());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().body("Usuario criado com sucesso!!!");

    }

}
