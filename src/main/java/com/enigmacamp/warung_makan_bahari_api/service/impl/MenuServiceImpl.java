package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu create(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Menu> getAll(String name, Long priceFrom, Long priceTo) {
        if (name == null || name.isEmpty()) {
            if (priceFrom == null || priceTo == null) {
                return menuRepository.findAll();
            }
            return menuRepository.findByPriceBetween(priceFrom, priceTo);
        }
        if (priceFrom == null || priceTo == null) {
            return menuRepository.findByNameIsContainingIgnoreCase(name);
        }
        return menuRepository.findByNameIsContainingIgnoreCaseAndPriceBetween(name, priceFrom, priceTo);

    }

    @Override
    public Menu update(Menu menu) {
        findByIdOrThrowNotFound(menu.getId());
        return menuRepository.save(menu);
    }

    @Override
    public void deleteById(String id) {
        menuRepository.deleteById(id);

    }

    private Menu findByIdOrThrowNotFound(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new RuntimeException("Menu not found"));
    }
}
