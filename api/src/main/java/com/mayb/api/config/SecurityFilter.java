package com.mayb.api.config;

import com.mayb.api.entity.User;
import com.mayb.api.repository.UserRepository;
import com.mayb.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        // Pega o token do cabeçalho (Header: Authorization)
        String token = recoverToken(request);

        // Se tiver token, valida ele
        if(token != null){
            String login = tokenService.validateToken(token); // Pega o email de dentro do token

            if(login != null && !login.isEmpty()){
                // Busca o usuário no banco
                User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));

                // Cria a "identidade" para o Spring Security entender que está logado
                var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

                // Salva no contexto (Agora o Spring sabe quem é você!)
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //Segue o baile (passa para o próximo filtro ou controller)
        filterChain.doFilter(request, response);
    }

    // Metodo auxiliar para limpar o "Bearer " do token
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        // O token vem assim: "Bearer eyJhbG..." -> Removemos o prefixo
        return authHeader.replace("Bearer ", "");
    }

}
