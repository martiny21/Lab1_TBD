package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.service.ClientService;
import com.example.TBDBackendLab1.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.TBDBackendLab1.configs.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/client")
public class ClientController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @Autowired
    public ClientController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("getemail/{email}")
    public ResponseEntity<ClientEntity> getEmail(@PathVariable String email) {
        ClientEntity client = clientService.getByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ClientEntity> getUser(@PathVariable Integer id){
        ClientEntity client = clientService.getById(id);
        if(client != null){
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody ClientEntity client) {
        try {
            client.setContrasena(passwordEncoder.encode((client.getContrasena())));
            clientService.addClient(client); // Guardar cliente
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public LoginDto login(@RequestParam("email") String email,
                          @RequestParam("contrasena") String password){
        try {
            UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(login);

            String jwt = this.jwtUtil.create(email);

            LoginDto loginDto = new LoginDto();
            loginDto.setToken(jwt);
            return loginDto;
        } catch (BadCredentialsException e) {
            return new LoginDto();
        }
    }
}
