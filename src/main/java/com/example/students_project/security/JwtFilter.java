package com.example.students_project.security;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.UsersDto;
import com.google.gson.Gson;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static com.example.students_project.service.validation.StatusCode.*;
import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private Gson gson;


    private final HandlerExceptionResolver exceptionResolver;

    @Autowired
    public JwtFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.exceptionResolver = exceptionResolver;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")){
                String token = authorization.substring(7);
                if (jwtService.isExpired(token)){
                    response.getWriter().println(gson.toJson(ResponseDto.builder()
                            .code(VALIDATION_ERROR_CODE)
                            .message("Token is expired!")
                            .build()));
                    response.setContentType("application/json");
                    response.setStatus(400);
                }else {
                    UsersDto usersDto = jwtService.getSubject(token);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usersDto,null,usersDto.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request,response);
        } catch (ExpiredJwtException | SignatureException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
