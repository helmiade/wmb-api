package com.enigmacamp.warung_makan_bahari_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    @NotBlank
    private String menuName;
    @NotNull
    @Positive
    private Long price;
    private MultipartFile image;
}
