package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.DataOverViewQueryDTO;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PutMapping
    @ApiOperation("修改分类")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类");
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result pageCategory(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询");
        PageResult pageResult = categoryService.pageCategory(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result updateStatus(@PathVariable Integer status,Integer id){

        log.info("启用禁用分类");
        categoryService.updateStatus(status,id);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类");
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteCategory(Integer id){
        log.info("删除分类");
        categoryService.deleteCategory(id);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查询分类列表")
    public Result listCategory(Integer type) {
        log.info("查询分类列表");
        return Result.success(categoryService.listCategory(type));
    }

}
