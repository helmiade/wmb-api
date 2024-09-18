package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.TableRequest;
import com.enigmacamp.warung_makan_bahari_api.dto.response.TableResponse;
import com.enigmacamp.warung_makan_bahari_api.entity.Table;
import org.springframework.stereotype.Component;

@Component
public class TableMapper {
    public static Table mapToTable(TableRequest tableRequest) {
        return Table.builder()
                .tableName(tableRequest.getTableName())
                .build();
    }
    public static Table mapToTable(TableResponse tableResponse) {
        return Table.builder()
                .tableName(tableResponse.getTableName())
                .build();
    }
}
