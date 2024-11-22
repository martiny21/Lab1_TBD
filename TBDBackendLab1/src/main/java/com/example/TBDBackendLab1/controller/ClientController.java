package com.example.TBDBackendLab1.controller;

import com.example.TBDBackendLab1.persistence.entity.ClientEntity;
import com.example.TBDBackendLab1.persistence.entity.ClientQueryReport;
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

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin("*")
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

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody ClientEntity client) {
        try {
            client.setClient_password(passwordEncoder.encode((client.getClient_password())));
            clientService.addClient(client); // Guardar cliente
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public LoginDto login(@RequestParam("email") String email,
                          @RequestParam("password") String password){
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

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<ClientEntity> getByEmail(@PathVariable String email) {
        ClientEntity client = clientService.getByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Integer id){
        ClientEntity client = clientService.getById(id);
        if(client != null){
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // tema de triggers, por probar
    @GetMapping("/getReports")
    public List<ClientQueryReport> getReports(){
        return clientService.getReports();
    }
}
