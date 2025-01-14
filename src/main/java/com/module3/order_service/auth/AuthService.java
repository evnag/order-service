package com.module3.order_service.auth;

import com.module3.order_service.configuration.JwtService;
import com.module3.order_service.model.Role;
import com.module3.order_service.model.RoleName;
import com.module3.order_service.model.User;
import com.module3.order_service.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setFio(request.getFio());
        user.setEmail(request.getUsername());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(new Role(RoleName.USER));
        user.setCreatedAt(LocalDate.now());
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    public User login(AuthRequest request, Authentication authentication) {

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();

        List<Role> roles = new ArrayList<>();
        for (GrantedAuthority item : userDetails.getAuthorities()) {
            String authority = item.getAuthority();
            roles.add(new Role(RoleName.valueOf(authority.substring(5))));
        }

        User user = new User();
        user.setFio(userDetails.getFio());
        user.setEmail(userDetails.getUsername());
        user.setRole(roles.get(0));

        return user;
    }
}
