package br.com.backend.fatura.application.controller;

import br.com.backend.fatura.application.infra.Constrants;
import br.com.backend.fatura.application.model.user.AuthenticationDTO;
import br.com.backend.fatura.application.model.user.LoginResponseDTO;
import br.com.backend.fatura.application.model.user.User;
import br.com.backend.fatura.application.repo.UserRepository;
import br.com.backend.fatura.application.service.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
@Slf4j
@CrossOrigin
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

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User data, BindingResult bindingResult){
        try {
            if(this.userRepository.findByEmail(data.getEmail()) != null) {
                log.error(Constrants.USER_ALREADY_EXISTS);
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("error", Constrants.USER_ALREADY_EXISTS);
                return ResponseEntity.badRequest().body(responseBody);
            }
            if(bindingResult.hasErrors()){
                String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
                JSONObject jsonResponse = new JSONObject();

                log.error(Constrants.PASSWORDS_DO_NOT_MATCH + " - " + errorMessage);
                return ResponseEntity.badRequest().body(jsonResponse.put("errors", errorMessage).toString());
            }

            String token = tokenService.generateToken(data);

            data.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
            User newUser = userRepository.save(data);
            String userId = newUser.getId().toHexString();
            log.info(Constrants.USER_CREATED);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", Constrants.USER_CREATED);
            return ResponseEntity.ok().body(responseBody);
//            return ResponseEntity.ok()
//                    .body(
//                            Map.of(
//                                    "_id", userId,
//                                    "token", token
//                            )
//                    );

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
