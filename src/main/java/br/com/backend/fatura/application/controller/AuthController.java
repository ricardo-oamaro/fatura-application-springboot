package br.com.backend.fatura.application.controller;

import br.com.backend.fatura.application.infra.Constrants;
import br.com.backend.fatura.application.model.user.AuthenticationDTO;
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
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data, BindingResult bindingResult) {
        try {
            var authentication = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
            Map<String, Object > responseBody = new HashMap<>();
            var user = userRepository.findByEmail(data.getEmail());
            if(data.getEmail() == null || data.getEmail() == ""){
                log.error(Constrants.EMAIL_NOT_PROVIDED);
                responseBody.put(Constrants.ERROR_MESSAGE, Constrants.EMAIL_NOT_PROVIDED);
                return ResponseEntity.badRequest().body(responseBody);
            }
            if (user == null) {
                log.error(Constrants.USER_NOT_FOUND);
                responseBody.put(Constrants.ERROR_MESSAGE, Constrants.USER_NOT_FOUND);
                return ResponseEntity.badRequest().body(responseBody);
            }
            if(bindingResult.hasErrors()){
                String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
                JSONObject jsonResponse = new JSONObject();

                log.error(Constrants.WRONG_PASSWORD + " - " + errorMessage);
                return ResponseEntity.badRequest().body(jsonResponse.put(Constrants.ERROR_MESSAGE, errorMessage).toString());
            }
            var auth = authenticationManager.authenticate(authentication);
            var token = tokenService.generateToken(user);
            String userId = user.getId().toHexString();
            log.info(Constrants.LOGIN_SUCCESS);
            responseBody.put(Constrants.MESSAGE, Constrants.LOGIN_SUCCESS);
            return ResponseEntity.ok()
                    .body(
                            Map.of(
                                    "_id", userId,
                                    "token", token
                            )
                    );

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User data, BindingResult bindingResult){
        try {
            if(this.userRepository.findByEmail(data.getEmail()) != null) {
                log.error(Constrants.USER_ALREADY_EXISTS);
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put(Constrants.ERROR_MESSAGE, Constrants.USER_ALREADY_EXISTS);
                return ResponseEntity.badRequest().body(responseBody);
            }
            if(bindingResult.hasErrors()){
                String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
                JSONObject jsonResponse = new JSONObject();

                log.error(Constrants.PASSWORDS_DO_NOT_MATCH + " - " + errorMessage);
                return ResponseEntity.badRequest().body(jsonResponse.put(Constrants.ERROR_MESSAGE, errorMessage).toString());
            }

            data.setPassword(new BCryptPasswordEncoder().encode(data.getPassword()));
            userRepository.save(data);

            log.info(Constrants.USER_CREATED);
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put(Constrants.MESSAGE, Constrants.USER_CREATED);
            return ResponseEntity.ok().body(responseBody);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
