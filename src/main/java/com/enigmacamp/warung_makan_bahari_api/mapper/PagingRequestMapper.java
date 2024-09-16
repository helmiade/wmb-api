package com.enigmacamp.warung_makan_bahari_api.mapper;

import com.enigmacamp.warung_makan_bahari_api.dto.request.PagingRequest;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagingRequestMapper {
    public PagingRequest pagingRequest(Integer page, Integer size) {
        return PagingRequest.builder()
                .page(page)
                .size(size)
                .build();
    }

}
