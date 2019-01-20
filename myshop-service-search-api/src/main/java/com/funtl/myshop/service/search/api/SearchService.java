package com.funtl.myshop.service.search.api;

import com.funtl.myshop.service.search.domain.TbItemResult;

import java.util.List;

public interface SearchService {
    List<TbItemResult> search(String query, int page, int rows);
}
