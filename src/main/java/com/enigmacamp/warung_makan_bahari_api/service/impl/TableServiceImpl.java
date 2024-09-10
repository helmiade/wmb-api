package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import com.enigmacamp.warung_makan_bahari_api.repository.TableRepository;
import com.enigmacamp.warung_makan_bahari_api.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;

    @Override
    public Table create(Table table) {
        if(table.getTableName()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table name is required");
        Optional<Table> existingTableName= tableRepository.findByTableNameIgnoreCase(table.getTableName());
        if (existingTableName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Table already exists");
        }
        return tableRepository.save(table);
    }

    @Override
    public Table findByName(String name) {
        Optional<Table> existingTableName= tableRepository.findByTableNameIgnoreCase(name);
        if (existingTableName.isPresent()) {
            return existingTableName.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with name " + name + " not found");
    }

    @Override
    public List<Table> findAll() {
        return tableRepository.findAll();
    }

    @Override
    public Table update(Table table) {
        if (table.getId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table id cant be null");
        if(table.getTableName()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table name cant be null");
        try {
            findByIdOrThrowNotFound(table.getId());
            return tableRepository.save(table);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Table already exists");
        }

    }

    @Override
    public void delete(String id) {
        if (!tableRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with id " + id + " not found");
        }
        tableRepository.deleteById(id);
    }

    private Table findByIdOrThrowNotFound(String id) {
        return tableRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with id " + id + " not found"));
    }


}
