package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.constant.ERole;
import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterAdminRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.*;
import com.enigmacamp.warung_makan_bahari_api.mapper.AdminMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.CustomerMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.RegisterResponMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.UserCredentialMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.UserCredentialRepository;
import com.enigmacamp.warung_makan_bahari_api.service.AdminService;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
import com.enigmacamp.warung_makan_bahari_api.service.CustomerService;
import com.enigmacamp.warung_makan_bahari_api.service.RoleService;
import com.enigmacamp.warung_makan_bahari_api.security.JwtUtil;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final PasswordEncoder passwordEncoder;
    private final AdminMapper adminMapper;
    private final UserCredentialMapper userCredentialMapper;
    private final RegisterResponMapper registerResponMapper;
    private final CustomerMapper customerMapper;
    private final UserCredentialRepository userCredentialRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private final AdminService adminService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(RegisterCustomerRequest registerCustomerRequest) {
        validationUtil.validate(registerCustomerRequest);
        try {
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_CUSTOMER).build());
            UserCredential userCredential=userCredentialMapper.mapToUserCredential(registerCustomerRequest, role);
            userCredentialRepository.saveAndFlush(userCredential);
            Customer customer= customerMapper.customerMapper(registerCustomerRequest, userCredential);
            customerService.createNew(customer);
            return registerResponMapper.mapToRegisterResponse(userCredential);

        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) throws AuthenticationException {
        validationUtil.validate(authRequest);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token =jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(RegisterAdminRequest registerAdminRequest) {
        validationUtil.validate(registerAdminRequest);
        try {
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_ADMIN).build());
            UserCredential userCredential=userCredentialMapper.mapToUserCredential(registerAdminRequest, role);
            userCredentialRepository.saveAndFlush(userCredential);
            Admin admin= adminMapper.mapToAdmin(registerAdminRequest, userCredential);
            adminService.createAdmin(admin);
            return registerResponMapper.mapToRegisterResponse(userCredential);

        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
