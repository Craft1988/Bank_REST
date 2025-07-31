package com.example.bankcards.service;

import com.example.bankcards.dto.auth.request.AuthRequestDto;
import com.example.bankcards.dto.user.response.UserDto;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.AppUserDetails;
import com.example.bankcards.util.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserDto register(AuthRequestDto request) {
        String password = request.getPassword();
        User newUser = userMapper.toUser(request);
        newUser.setPassword(encoder.encode(password));

        return userMapper.toDto(userRepo.save(newUser));
    }


    public ResponseEntity<Void> login(AuthRequestDto authRequest) {
        Authentication auth =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequest.getUsername(), authRequest.getPassword()
                        )
                );
        AppUserDetails userDetails = (AppUserDetails) auth.getPrincipal();
        String token = jwtTokenManager.createToken(
                authRequest.getUsername(),
                userDetails.user().getRole()
        );
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

    }
}
