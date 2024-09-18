package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TableResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class TableResponseMapper {
    public static TableResponse mapToTableResponse(Table table) {
        return TableResponse.builder()
                .tableName(table.getTableName())
                .build();
    }
}
