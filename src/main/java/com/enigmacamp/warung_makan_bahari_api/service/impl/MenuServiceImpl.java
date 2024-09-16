package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.mapper.MenuResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;
    private final MenuResponseMapper menuResponseMapper;

    @Override
    public MenuResponse create(MenuRequest menuRequest) {
        validationUtil.validate(menuRequest);
        Menu menu = Menu.builder()
                .name(menuRequest.getMenuName())
                .price(menuRequest.getPrice())
                .build();
        menuRepository.saveAndFlush(menu);
        return menuResponseMapper.mapToMenuResponse(menu);
    }

    @Override
    public MenuResponse getById(String id) {
        Menu menu = menuRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found"));
        return menuResponseMapper.mapToMenuResponse(menu);
    }


    @Override
    public Page<Menu> getAll(PagingRequest request) {
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize());
        return menuRepository.findAll(pageable);
    }

    @Override
    public MenuResponse update(Menu menu) {
        findByIdOrThrowNotFound(menu.getId());
        menuRepository.saveAndFlush(menu);
        return menuResponseMapper.mapToMenuResponse(menu);

    }

    @Override
    public void deleteById(String id) {
        menuRepository.deleteById(id);

    }

    private Menu findByIdOrThrowNotFound(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "menu Id not found"));
    }
}
