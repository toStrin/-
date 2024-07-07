package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface CategoryMapper {

    @Update("update category set name = #{name},sort = #{sort},type = #{type} where id = #{id}")
    void updateCategory(Category category);

    Page<Category> list(CategoryPageQueryDTO categoryPageQueryDTO);

    @Update("update category set status = #{status} where id = #{id}")
    void updateStatus(Integer status, long id);

    @Insert("insert into category(name,sort,type,create_time,update_time) values(#{name}, #{sort}, #{type},now(),now())")
    void addCategory(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteCategory(Integer id);

    @Select("select * from category where type = #{type}")
    List listCategory(Integer type);
}
