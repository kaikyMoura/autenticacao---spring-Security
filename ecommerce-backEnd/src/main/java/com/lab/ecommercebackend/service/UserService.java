package com.lab.ecommercebackend.service;

import com.lab.ecommercebackend.dao.CrudDao;
import com.lab.ecommercebackend.dataAcess.UserDetailsImpl;
import com.lab.ecommercebackend.dto.CreateUserDto;
import com.lab.ecommercebackend.dto.LoginUserDto;
import com.lab.ecommercebackend.dto.RecoveryJwtTokenDto;
import com.lab.ecommercebackend.enums.RoleName;
import com.lab.ecommercebackend.model.Role;
import com.lab.ecommercebackend.model.User;
import com.lab.ecommercebackend.repository.IUserRepository;
import com.lab.ecommercebackend.security.JwtTokenService;
import com.lab.ecommercebackend.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements CrudDao<CreateUserDto, UUID> {

    private final IUserRepository userRepository;

    private final JwtTokenService jwtTokenService;

    private final SecurityConfig securityConfig;

    @Autowired
    public UserService(IUserRepository userRepositoy, JwtTokenService jwtTokenService, SecurityConfig securityConfig) {
        this.userRepository = userRepositoy;
        this.jwtTokenService = jwtTokenService;
        this.securityConfig = securityConfig;
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            // Recupera o usuário pelo email fornecido
            User user = userRepository.findByEmail(loginUserDto.email())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + loginUserDto.email()));

            // Verifica se a senha fornecida corresponde à senha armazenada (hash)
            if (!encoder.matches(loginUserDto.password(), user.getPassword())) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            // Gera um token JWT para o usuário autenticado
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            String token = jwtTokenService.generateToken(userDetails);
            return new RecoveryJwtTokenDto(token);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erro ao autenticar o usuário", exception);
        }
    }

    @Override
    public void create(CreateUserDto createUserDto) {
        try {
            String role = createUserDto.role() == null ? "ROLE_CUSTOMER" : createUserDto.role().toString();

            userRepository.save(User.builder()
                    .id(UUID.randomUUID())
                    .email(createUserDto.email())
                    .password(securityConfig.passwordEncoder().encode(createUserDto.password()))
                    .roles(List.of(Role.builder().name(RoleName.valueOf(role)).build()))
                    .build());
        }
        catch (Exception e) {
            throw new RuntimeException("Erro ao executar a operação", e);
        }
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        try {
            this.getByID(id);
            userRepository.deleteById(id);
        }
        catch (Exception exception) {
            throw new Exception("Erro ao executar a operação!");
        }
    }

    @Override
    public void update(UUID uuid, CreateUserDto createUserDto) {

    }

    @Override
    public void getByID(UUID id) throws Exception {
        userRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado!"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Page<User> getPageable(Pageable pageable) {
        return null;
    }
}