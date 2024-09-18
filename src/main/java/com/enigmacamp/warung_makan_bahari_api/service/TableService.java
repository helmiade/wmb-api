package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TableResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;

import java.util.List;

public interface TableService {
    TableResponse create(TableRequest request);
    TableResponse findByName(String name);
    TableResponse findById(String id);
    List<Table> findAll();
    TableResponse update(TableRequest request);
    void delete(String id);
}
