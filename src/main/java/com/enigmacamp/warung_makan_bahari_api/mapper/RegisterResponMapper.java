package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RegisterResponMapper {
    public RegisterResponse mapToRegisterResponse(UserCredential userCredential){
        return RegisterResponse.builder()
                .username(userCredential.getUsername())
                .role(userCredential.getRole().getName().toString())
                .build();
    }
}
