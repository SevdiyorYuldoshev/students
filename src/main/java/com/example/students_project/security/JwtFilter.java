package com.example.students_project.security;

import com.example.students_project.dto.ResponseDto;
import com.example.students_project.dto.UsersDto;
import com.google.gson.Gson;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.example.students_project.service.validation.StatusCode.*;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Gson gson;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
    }
}
