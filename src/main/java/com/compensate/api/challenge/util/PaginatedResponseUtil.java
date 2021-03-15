package com.compensate.api.challenge.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginatedResponseUtil {

    public <T> Page<T> getPage(List<T> allItems, Pageable pageable) {
        if (allItems.isEmpty()) {
            return Page.empty();
        }

        final int start = Math.min((int)pageable.getOffset(), allItems.size());
        final int end = Math.min((start + pageable.getPageSize()), allItems.size());

        return new PageImpl<T>(allItems.subList(start, end), pageable, allItems.size());
    }
}
