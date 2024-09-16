package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import com.enigmacamp.warung_makan_bahari_api.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TableController {
    private final TableService tableService;

    @PostMapping
    public Table createNewTable(@RequestBody Table table) {
        return tableService.create(table);
    }

    //get by id
    @GetMapping("/{name}")
    public Table getTableByName(@PathVariable String name) {
        return tableService.findByName(name);
    }

    //get all customer
    @GetMapping
    public List<Table> getAllCustomers() {
        return tableService.findAll();
    }

    //update
    @PutMapping
    public Table updateTable(@RequestBody Table table) {
        return tableService.update(table);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        tableService.delete(id);
    }
}
