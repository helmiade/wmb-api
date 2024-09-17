package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.mapper.CommonResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingRequestMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.PagingResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final CommonResponseMapper commonResponseMapper;
    private final PagingResponseMapper pagingResponseMapper;
    private final PagingRequestMapper pagingRequestMapper;

    @GetMapping
    public ResponseEntity<?> getAllMenu(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "5") Integer size) {
        PagingRequest request = pagingRequestMapper.pagingRequest(page, size);
        Page<Menu> menus=menuService.getAll(request);
        PagingResponse pagingResponse = pagingResponseMapper.pagingResponseToMapper(menus,page,size);
        CommonResponse<List<Menu>> response= commonResponseMapper.commonResponseToMap(menus,pagingResponse);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }

    @GetMapping("/{id}")
    public MenuResponse findById(@PathVariable String id) {
        return menuService.getById(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MenuResponse update(@RequestBody Menu menu) {
        return menuService.update(menu);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public MenuResponse createNewMenu(@RequestBody MenuRequest menuRequest) {
        return menuService.create(menuRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable String id) {
        menuService.deleteById(id);
    }

}
