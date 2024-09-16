package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterAdminRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import lombok.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UserCredentialMapper {
    private final PasswordEncoder passwordEncoder;
    public UserCredential mapToUserCredential(RegisterCustomerRequest registerCustomerRequest, Role role) {
        return UserCredential.builder()
                .username(registerCustomerRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(registerCustomerRequest.getPassword()))
                .role(role)
                .build();
    }

    public UserCredential mapToUserCredential(RegisterAdminRequest registerAdminRequest, Role role) {
        return UserCredential.builder()
                .username(registerAdminRequest.getUsername().toLowerCase())
                .password(passwordEncoder.encode(registerAdminRequest.getPassword()))
                .role(role)
                .build();
    }
}
