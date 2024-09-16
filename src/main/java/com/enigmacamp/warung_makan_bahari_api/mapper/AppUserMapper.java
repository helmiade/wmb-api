package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.entity.AppUser;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppUserMapper {
    public AppUser mapToAppUser(UserCredential userCredential) {
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}
