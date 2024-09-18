package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class TableRequestMapper {
    public static TableRequest mapToTableRequest(Table table) {
        return TableRequest.builder()
                .tableName(table.getTableName())
                .build();
    }
}
