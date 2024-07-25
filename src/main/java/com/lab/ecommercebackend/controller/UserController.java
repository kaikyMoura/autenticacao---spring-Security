package com.lab.ecommercebackend.controller;

import com.lab.ecommercebackend.dto.CreateUserDto;
import com.lab.ecommercebackend.dto.LoginUserDto;
import com.lab.ecommercebackend.dto.RecoveryJwtTokenDto;
import com.lab.ecommercebackend.dto.RecoveryUserDto;
import com.lab.ecommercebackend.model.User;
import com.lab.ecommercebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        try {
            RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto user) {
        try {
            userService.create(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

    @GetMapping("/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }
    
    @GetMapping("/auth/getUser")
    public ResponseEntity<RecoveryUserDto> getUser(@Param(value = "email") String email) {
    	return ResponseEntity.ok().body(userService.getUser(email));
    }
    
    
    //Apenas usuários com o role "ADMINISTRATOR" poderão acessar esses endpoints
    @GetMapping("/administrator")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }
    
    @DeleteMapping("/administrator/delete/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> deleteUser(@PathVariable String id) throws Exception {
    	try {
    	UUID uuid = UUID.fromString(id);
    	userService.deleteById(uuid);
        return new ResponseEntity<>("Usuário deletado com sucesso!", HttpStatus.ACCEPTED);
    	}
    	catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
    }

    @GetMapping(value = "/administrator/getAll")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }
}
