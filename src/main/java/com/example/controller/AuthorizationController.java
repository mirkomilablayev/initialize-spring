package com.example.controller;

import com.example.configuration.jwt.JwtUtils;
import com.example.dto.CommonResponse;
import com.example.dto.auth.JwtResponse;
import com.example.dto.auth.LoginRequest;
import com.example.dto.auth.SignupRequest;
import com.example.entity.User;
import com.example.entity.UserRole;
import com.example.exceptions.GenericException;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.util.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@Tag(name = "AUTHORIZATION-CONTROLLER", description = "Authorization management controller")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<CommonResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User principal = (User) authenticate.getPrincipal();
        String jwt = jwtUtils.generateToken(principal);
        return ResponseEntity.ok(new CommonResponse(new JwtResponse(jwt)));
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new GenericException("Username is already taken");
        }

        User user = new User();
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());
        user.setAbout(signUpRequest.getAbout());
        user.setFullName(signUpRequest.getFullName());
        Set<UserRole> roles = new HashSet<>();

        UserRole userRole = roleRepository.findByName(Constants.USER_ROLE)
                .orElseThrow(() -> new GenericException(HttpStatus.NOT_FOUND, "User role not found"));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new CommonResponse("User is successfully saved!"));
    }
}
