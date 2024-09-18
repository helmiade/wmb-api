package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.MenuRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.MenuResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Menu;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
    MenuResponse create(MenuRequest menuRequest);
    MenuResponse getById(String id);
    Page<Menu> getAll(PagingRequest request);
    MenuResponse update(Menu menu);
    void deleteById(String id);
    Resource getImageByMenuId(String id);

//    Menu findById(String id);
}
