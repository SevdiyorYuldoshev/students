package com.example.students_project.security;

import com.example.students_project.dto.UsersDto;
import com.example.students_project.entity.UserSession;
import com.example.students_project.repository.UserSessionRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtService {
    @Value(value = "${spring.security.secret.key}")
    private String secretKey;

    @Autowired
    private Gson gson;
//    @Autowired
//    private UserSessionRepository userSessionRepository;

    public static Map<String, UsersDto> redis = new HashMap<>();

    public String generateToken(UsersDto usersDto){
        String uuid = UUID.randomUUID().toString();
//        userSessionRepository.save(new UserSession(uuid, gson.toJson(usersDto)));
        redis.put(uuid, usersDto);
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *2))
                .setSubject(uuid)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isExpired(String token){
        return getClaims(token).getExpiration().getTime() < System.currentTimeMillis();
    }
    public UsersDto getSubject(String token){
        String uuid = getClaims(token).getSubject();
//        return userSessionRepository.findById(uuid).map(s -> gson.fromJson(s.getUserinfo(), UsersDto.class))
//                .orElseThrow(()-> new JwtException("Token is expired"));

        return redis.get(uuid);

    }
}