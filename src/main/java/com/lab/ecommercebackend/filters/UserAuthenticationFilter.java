package com.lab.ecommercebackend.filters;

import com.lab.ecommercebackend.dataAcess.UserDetailsImpl;
import com.lab.ecommercebackend.model.User;
import com.lab.ecommercebackend.repository.IUserRepository;
import com.lab.ecommercebackend.security.JwtTokenService;
import com.lab.ecommercebackend.security.SecurityConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final IUserRepository userRepository;

    public UserAuthenticationFilter(JwtTokenService jwtTokenService, IUserRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        			//Verifica se o endpoint não é público. Se a resposta for "true", então será gerado o token de acesso
        	        if (checkIfEndpointIsNotPublic(request)) {
        	            String token = recoveryToken(request);
        	            
        	            if (token != null) {
        	            	//Se o token não for nulo, ele irá recuperar o usuário através do token
        	                String subject = jwtTokenService.getSubjectFromToken(token);
        	                Optional<User> optionalUser = userRepository.findByEmail(subject);

        	                if (optionalUser.isPresent()) {
        	                    User user = optionalUser.get();
        	                    UserDetailsImpl userDetails = new UserDetailsImpl(user);

        	                    Authentication authentication = new UsernamePasswordAuthenticationToken(
        	                            userDetails, null, userDetails.getAuthorities());

        	                    SecurityContextHolder.getContext().setAuthentication(authentication);

        	                    //Verifica se o endpoint que está sendo necessita da permissão de ADMIN
        	                    if (requiresAdminAccess(request) && !userDetails.getAuthorities().stream()
        	                            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
        	                        throw new AccessDeniedException("Usuário não tem permissão para acessar este endpoint");
        	                    }
        	                } else {
        	                    throw new RuntimeException("Usuário não encontrado!");
        	                }
        	            } else {
        	                throw new RuntimeException("O token está ausente!");
        	            }
        	        }
        	        
        	        filterChain.doFilter(request, response);
        	        
        	    } catch (AccessDeniedException ade) {
        	    	ade.printStackTrace();
        	        response.sendError(HttpServletResponse.SC_FORBIDDEN, ade.getMessage());
        	    } catch (Exception e) {
        	        e.printStackTrace();
        	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno do servidor");
        	    }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        return !Arrays.stream(SecurityConfig.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED)
                .anyMatch(pattern -> request.getRequestURI().matches(pattern.replace("**", ".*")));
    }
    
    private boolean requiresAdminAccess(HttpServletRequest request) {
        return Arrays.stream(SecurityConfig.ENDPOINTS_ADMIN)
                .anyMatch(pattern -> request.getRequestURI().matches(pattern.replace("**", ".*")));
    }

}
