package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.FileResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import com.enigmacamp.warung_makan_bahari_api.entity.MenuImage;
import com.enigmacamp.warung_makan_bahari_api.mapper.MenuResponseMapper;
import com.enigmacamp.warung_makan_bahari_api.repository.MenuRepository;
import com.enigmacamp.warung_makan_bahari_api.service.MenuImageService;
import com.enigmacamp.warung_makan_bahari_api.service.MenuService;
import com.enigmacamp.warung_makan_bahari_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;
    private final MenuResponseMapper menuResponseMapper;
    private final MenuImageService menuImageService;

    @Override
    public MenuResponse create(MenuRequest menuRequest) {
        validationUtil.validate(menuRequest);
        MenuImage menuImage = new MenuImage();
        if(!menuRequest.getImage().isEmpty()) menuImage = menuImageService.createFile(menuRequest.getImage());
        Menu menu = Menu.builder()
                .name(menuRequest.getMenuName())
                .price(menuRequest.getPrice())
                .menuImage(menuImage)
                .build();
        menuRepository.saveAndFlush(menu);
        FileResponse fileResponse= FileResponse.builder()
                .fileName(menu.getMenuImage().getName())
                .url("/api/menu/"+menu.getId()+"/image")
                .build();

        return menuResponseMapper.mapToMenuResponse(menu, fileResponse);
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
    @Transactional(readOnly= true)
    @Override
    public Resource getImageByMenuId(String id) {
        Menu menu = findByIdOrThrowNotFound(id);
        Resource resource = menuImageService.findByPath(menu.getMenuImage().getPath());
        return resource;
    }



    private Menu findByIdOrThrowNotFound(String id) {
        return menuRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "menu Id not found"));
    }
}
