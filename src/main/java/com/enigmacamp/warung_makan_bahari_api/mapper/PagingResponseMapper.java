package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.response.PagingResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class PagingResponseMapper {
    public <T>PagingResponse pagingResponseToMapper(Page<T> pageName, Integer page, Integer size){
        return PagingResponse.builder()
                .page(page)
                .size(size)
                .count(pageName.getTotalElements())
                .totalPages(pageName.getTotalPages())
                .build();
    }
}
