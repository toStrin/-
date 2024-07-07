package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;


public interface CategoryService {
    void updateCategory(CategoryDTO categoryDTO);

    PageResult pageCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateStatus(Integer status, long id);

    void addCategory(CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    List<Category> listCategory(Integer type);
}
