package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterAdminRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.mapper.CommonResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final CommonResponseMapper commonResponseMapper;
    private final AuthService authService;
    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterCustomerRequest registerCustomerRequest) {
        RegisterResponse registerResponse = authService.registerCustomer(registerCustomerRequest);
        CommonResponse<RegisterResponse> response= commonResponseMapper.commonResponseToMap(registerResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse loginResponse = authService.login(authRequest);
        CommonResponse<LoginResponse> response = commonResponseMapper.commonResponseToMap(loginResponse);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterAdminRequest registerAdminRequest) {
        RegisterResponse registerResponse = authService.registerAdmin(registerAdminRequest);
        CommonResponse<RegisterResponse> response= commonResponseMapper.commonResponseToMap(registerResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(response);
    }
}
