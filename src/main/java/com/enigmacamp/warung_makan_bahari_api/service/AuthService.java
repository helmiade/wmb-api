package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.AuthRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterAdminRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.RegisterCustomerRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.LoginResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(RegisterCustomerRequest registerCustomerRequest);
    RegisterResponse registerAdmin(RegisterAdminRequest registerAdminRequest);

    LoginResponse login(AuthRequest authRequest);
}
