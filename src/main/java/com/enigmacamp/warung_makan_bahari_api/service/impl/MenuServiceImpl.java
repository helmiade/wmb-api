package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;

    @Override
    public MenuResponse create(MenuRequest menuRequest) {
        validationUtil.validate(menuRequest);
        Menu menu = Menu.builder()
                .name(menuRequest.getMenuName())
                .price(menuRequest.getPrice())
                .build();
        menuRepository.saveAndFlush(menu);
        MenuResponse menuResponse = MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getName())
                .price(menu.getPrice())
                .build();

        return menuResponse;
    }

    @Override
    public MenuResponse getById(String id) {
        Menu menu = menuRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found"));
        return MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getName())
                .price(menu.getPrice())
                .build();
    }

    public Menu findById(String id) {
        return menuRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



//    @Override
//    public List<Menu> getAll(String name, Long priceFrom, Long priceTo) {
//        if (name == null || name.isEmpty()) {
//            if (priceFrom == null || priceTo == null) {
//                return menuRepository.findAll();
//            }
//            return menuRepository.findByPriceBetween(priceFrom, priceTo);
//        }
//        if (priceFrom == null || priceTo == null) {
//            return menuRepository.findByNameIsContainingIgnoreCase(name);
//        }
//        return menuRepository.findByNameIsContainingIgnoreCaseAndPriceBetween(name, priceFrom, priceTo);
//
//    }

    @Override
    public Page<Menu> getAll(PagingRequest request) {
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize());
        return menuRepository.findAll(pageable);
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
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "menu Id not found"));
    }
}
