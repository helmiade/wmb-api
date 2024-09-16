package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterAdminRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Admin;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminMapper {
    public Admin mapToAdmin(RegisterAdminRequest registerAdminRequest, UserCredential userCredential) {
        return Admin.builder()
                .name(registerAdminRequest.getName())
                .userCredential(userCredential)
                .build();
    }
}
