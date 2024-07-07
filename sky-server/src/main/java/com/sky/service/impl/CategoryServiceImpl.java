package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.updateCategory(category);
    }

    @Override
    public PageResult pageCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("页号：{},每页大小：{}",categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.list(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());

    }

    @Override
    public void updateStatus(Integer status, long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId()).build();
        log.info("启用禁用分类：{}",id);
        categoryMapper.updateStatus(status, id);
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        log.info("新增分类：{}",categoryDTO);
        Category category = new Category();
        BeanUtils .copyProperties(categoryDTO,category);
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setStatus(StatusConstant.DISABLE);
        categoryMapper.addCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        log.info("删除分类：{}",id);
        //TODO   分类下有菜品的异常处理
        categoryMapper.deleteCategory(id);
    }

    @Override
    public List<Category> listCategory(Integer type) {
        log.info("查询分类：{}",type);
        return categoryMapper.listCategory(type);
    }


}
