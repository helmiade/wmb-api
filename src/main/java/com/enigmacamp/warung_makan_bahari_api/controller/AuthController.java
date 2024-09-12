package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;
import com.enigmacamp.warung_makan_bahari_api.service.AuthService;
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
    private final AuthService authService;
    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.registerCustomer(authRequest);
        CommonResponse<RegisterResponse> response= CommonResponse.<RegisterResponse>builder()
                .message("successfully register new customer")
                .statusCode(HttpStatus.CREATED.value())
                .data(registerResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse loginResponse = authService.login(authRequest);
        CommonResponse<LoginResponse> response= CommonResponse.<LoginResponse>builder()
                .message("successfully login customer")
                .statusCode(HttpStatus.OK.value())
                .data(loginResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }
}
