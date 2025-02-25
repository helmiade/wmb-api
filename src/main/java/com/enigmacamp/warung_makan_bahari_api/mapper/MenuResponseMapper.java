package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.response.FileResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuResponseMapper {
    public MenuResponse mapToMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    public MenuResponse mapToMenuResponse(Menu menu, FileResponse fileResponse) {
        return MenuResponse.builder()
                .id(menu.getId())
                .image(fileResponse)
                .menuName(menu.getName())
                .price(menu.getPrice())
                .build();
    }
}
