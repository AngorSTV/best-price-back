package ru.angorstv.bestprice.common;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Data
public class SearchRequest {
    private static final int defaultPageSize = 20;
    private String term;
    private Sorting sorting;
    private Direct direct;
    private int page;
    private int pageSize;

    public SearchRequest(@NonNull String term, Sorting sorting, Direct direct, Integer page, Integer pageSize) {
        this.term = term;
        this.sorting = Objects.requireNonNullElse(sorting, Sorting.PRICE);
        this.direct = Objects.requireNonNullElse(direct, Direct.ASC);
        this.page = Objects.requireNonNullElse(page, 0);
        this.pageSize = Objects.requireNonNullElse(pageSize, defaultPageSize);
    }
}
