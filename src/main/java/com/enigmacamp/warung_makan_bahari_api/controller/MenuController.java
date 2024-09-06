package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<Menu> getAllMenu(@RequestParam(required = false) String name, @RequestParam(required = false) Long priceFrom, @RequestParam(required = false) Long priceTo) {
        return menuService.getAll(name, priceFrom, priceTo);
    }

    @GetMapping("/{id}")
    public Menu findById(@PathVariable String id) {
        return menuService.getById(id);
    }

    @PutMapping
    public Menu update(@RequestBody Menu menu) {
        return menuService.update(menu);
    }

    @PostMapping
    public Menu createNewMenu(@RequestBody Menu menu) {
        return menuService.create(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        menuService.deleteById(id);
    }

}
