package com.enigmacamp.warung_makan_bahari_api.dto.request;

import com.enigmacamp.warung_makan_bahari_api.entity.UserCredential;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCustomerRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "password must greater than 8, contain Capital and special case")
    private String password;
    private String name;
    private String phoneNumber;
    private Boolean isMember=false;
}
