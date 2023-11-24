package com.example.controller;

import com.example.entity.User;
import com.example.exceptions.GenericException;
import com.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow(() -> new GenericException(username + " not found!"));
        return User.build(user);
    }
}
