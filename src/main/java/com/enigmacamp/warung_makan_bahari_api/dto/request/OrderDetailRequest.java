package com.enigmacamp.warung_makan_bahari_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private String MenuId;
    private Integer quantity;
}
