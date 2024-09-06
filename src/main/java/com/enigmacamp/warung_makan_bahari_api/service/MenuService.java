package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu create(Menu menu);
    Menu getById(String id);
    List<Menu> getAll(String name, Long priceFrom, Long priceTo);
    Menu update(Menu menu);
    void deleteById(String id);
}
