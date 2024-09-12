package com.enigmacamp.warung_makan_bahari_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
