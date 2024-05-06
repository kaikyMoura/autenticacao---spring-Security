package com.lab.ecommercebackend.service;

import com.lab.ecommercebackend.dao.CrudDao;
import com.lab.ecommercebackend.dataAcess.UserDetailsImpl;
import com.lab.ecommercebackend.dto.CreateUserDto;
import com.lab.ecommercebackend.dto.LoginUserDto;
import com.lab.ecommercebackend.dto.RecoveryJwtTokenDto;
import com.lab.ecommercebackend.enums.RoleName;
import com.lab.ecommercebackend.model.Role;
import com.lab.ecommercebackend.model.User;
import com.lab.ecommercebackend.repository.IRoleRepository;
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

    private final IRoleRepository roleRepository;

    private final JwtTokenService jwtTokenService;

    private final SecurityConfig securityConfig;

    @Autowired
    public UserService(IUserRepository userRepositoy, IRoleRepository roleRepository, JwtTokenService jwtTokenService, SecurityConfig securityConfig) {
        this.userRepository = userRepositoy;
        this.roleRepository = roleRepository;
        this.jwtTokenService = jwtTokenService;
        this.securityConfig = securityConfig;
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = userRepository.findByEmail(loginUserDto.email())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + loginUserDto.email()));

            if (!encoder.matches(loginUserDto.password(), user.getPassword())) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            // Gera um token JWT para o usuário autenticado
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            String token = jwtTokenService.generateToken(userDetails);
            return new RecoveryJwtTokenDto(token);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException("Erro ao autenticar o usuário", exception);
        }
    }

    @Override
    public void create(CreateUserDto createUserDto) {
        try {
            String requestRole = createUserDto.role() == null ? "ROLE_CUSTOMER" : createUserDto.role();

            Role role = roleRepository.findByName(RoleName.valueOf(requestRole));

            userRepository.save(User.builder()
                    .id(UUID.randomUUID())
                    .email(createUserDto.email())
                    .password(securityConfig.passwordEncoder().encode(createUserDto.password()))
                    .role(Role.builder().name(RoleName.valueOf(requestRole)).id(role.getId()).build())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar a operação", e);
        }
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        try {
            this.getByID(id);
            userRepository.deleteById(id);
        } catch (Exception exception) {
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