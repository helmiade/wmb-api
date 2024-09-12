package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Role;
import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import com.enigmacamp.warung_makan_bahari_api.repository.UserCredentialRepository;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import com.enigmacamp.warung_makan_bahari_api.util.JwtUtil;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest authRequest) {
        try {
            //role
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());

            //user credential
            UserCredential userCredential = UserCredential.builder()
                    .username(authRequest.getUsername())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();

            userCredentialRepository.saveAndFlush(userCredential);

            //customer
            Customer customer= Customer.builder()
                    .userCredential(userCredential)
                    .build();

            customerService.createNew(customer);
            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();

        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        validationUtil.validate(authRequest);

        //tempat logic utk login
        String token =jwtUtil.generateToken("");

        return LoginResponse.builder()
                .token(token)
                .role("ROLE_CUSTOMER")
                .build();
    }
}
