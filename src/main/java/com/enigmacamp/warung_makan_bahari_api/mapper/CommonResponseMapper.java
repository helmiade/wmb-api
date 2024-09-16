package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.response.CommonResponse;
import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CommonResponseMapper {
    public <T> CommonResponse<T> commonResponseToMap(T data) {
        return CommonResponse.<T>builder()
                .message("Successfully processed")
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build();
    }
    public <T> CommonResponse<List<T>> commonResponseToMap(Page<T> pageData, PagingResponse pagingResponse) {
        List<T> responseList = pageData.getContent();

        return CommonResponse.<List<T>>builder()
                .message("Successfully processed")
                .statusCode(HttpStatus.OK.value())
                .paging(pagingResponse)
                .data(responseList)
                .build();
    }

}

