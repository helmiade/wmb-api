package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> getAllMenu(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "5") Integer size) {
        PagingRequest request = PagingRequest.builder()
                .page(page)
                .size(size)
                .build();

        Page<Menu> menus=menuService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .count(menus.getTotalElements())
                .totalPages(menus.getTotalPages())
                .build();

        CommonResponse<List<Menu>> response= CommonResponse.<List<Menu>>builder()
                .message("successfully get all customer")
                .statusCode(HttpStatus.OK.value())
                .data(menus.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }

    @GetMapping("/{id}")
    public MenuResponse findById(@PathVariable String id) {
        return menuService.getById(id);
    }

    @PutMapping
    public Menu update(@RequestBody Menu menu) {
        return menuService.update(menu);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MenuResponse createNewMenu(@RequestBody MenuRequest menuRequest) {
        return menuService.create(menuRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        menuService.deleteById(id);
    }

}
