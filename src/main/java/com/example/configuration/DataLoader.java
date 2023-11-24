package com.example.configuration;


import com.example.entity.User;
import com.example.entity.UserRole;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) {
        if (ddl.equalsIgnoreCase("create") || ddl.equalsIgnoreCase("create-drop")) {
            UserRole userRole = roleRepo.save(new UserRole(Constants.USER_ROLE));
            UserRole adminRole = roleRepo.save(new UserRole(Constants.ADMIN_ROLE));

            User user = new User();
            user.setFullName("administrator");
            user.setAbout("this is admin from the system");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin_1212"));
            user.setRoles(new HashSet<>(List.of(adminRole, userRole)));
            userRepo.save(user);

        }
    }
}
