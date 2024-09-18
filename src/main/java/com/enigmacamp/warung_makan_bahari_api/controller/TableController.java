package com.enigmacamp.warung_makan_bahari_api.controller;

import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TableResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Customer;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import com.enigmacamp.warung_makan_bahari_api.service.TableService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/table")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authenticator")
@PreAuthorize("hasRole('ADMIN')")
public class TableController {
    private final TableService tableService;

    @PostMapping
    public TableResponse createNewTable(@RequestBody TableRequest request) {
        return tableService.create(request);
    }

    //get by id
    @GetMapping("/{name}")
    public TableResponse getTableByName(@PathVariable String name) {
        return tableService.findByName(name);
    }

    //get all customer
    @GetMapping
    public List<Table> getAllCustomers() {
        return tableService.findAll();
    }

    //update
    @PutMapping
    public TableResponse updateTable(@RequestBody TableRequest request) {
        return tableService.update(request);
    }

    //delete by id
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable String id) {
        tableService.delete(id);
    }
}
