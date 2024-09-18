package com.enigmacamp.warung_makan_bahari_api.service.impl;

import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TableResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import com.enigmacamp.warung_makan_bahari_api.mapper.TableMapper;
import com.enigmacamp.warung_makan_bahari_api.mapper.TableResponseMapper;
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
    public TableResponse create(TableRequest request) {
        if(request.getTableName()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table name is required");
        Optional<Table> existingTableName= tableRepository.findByTableNameIgnoreCase(request.getTableName());
        if (existingTableName.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Table already exists");
        }
        Table table = TableMapper.mapToTable(request);
        tableRepository.save(table);
        return TableResponseMapper.mapToTableResponse(table);
    }

    @Override
    public TableResponse findByName(String name) {
        Optional<Table> existingTableName= tableRepository.findByTableNameIgnoreCase(name);
        if (existingTableName.isPresent()) {
            Table table= existingTableName.get();
            return TableResponseMapper.mapToTableResponse(table);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with name " + name + " not found");
    }

    @Override
    public TableResponse findById(String id) {
        Table table= tableRepository.findById(id).orElse(null);
        return TableResponseMapper.mapToTableResponse(table);
    }

    @Override
    public List<Table> findAll() {
        return tableRepository.findAll();
    }

    @Override
    public TableResponse update(TableRequest request) {
        Table table= TableMapper.mapToTable(request);
        if (table.getId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table id cant be null");
        if(table.getTableName()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table name cant be null");
        try {
            Table foundTable=findByIdOrThrowNotFound(table.getId());
            tableRepository.save(table);
            return TableResponseMapper.mapToTableResponse(foundTable);
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
