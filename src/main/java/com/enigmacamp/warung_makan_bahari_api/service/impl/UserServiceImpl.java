package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.AppUser;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.repository.UserCredentialRepository;
import com.enigmacamp.warung_makan_bahari_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserCredentialRepository userCredentialRepository;



    //verifikasi jwt
    @Override
    public AppUser loadUserByUserId(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid Credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }


//verifikasi authentikasi login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid Credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}
