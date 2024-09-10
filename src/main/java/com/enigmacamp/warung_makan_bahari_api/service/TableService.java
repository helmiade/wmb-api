package com.enigmacamp.warung_makan_bahari_api.service;

import com.enigmacamp.warung_makan_bahari_api.entity.Table;

import java.util.List;

public interface TableService {
    Table create(Table table);
    Table findByName(String name);
    List<Table> findAll();
    Table update(Table table);
    void delete(String id);
}
