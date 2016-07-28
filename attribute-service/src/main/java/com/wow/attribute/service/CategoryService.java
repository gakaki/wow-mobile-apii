package com.wow.attribute.service;

import com.wow.attribute.model.Category;
import com.wow.attribute.vo.response.CategoryListResponse;
import com.wow.attribute.vo.response.CategoryResponse;
import com.wow.attribute.vo.response.CategorySecondResponse;
import com.wow.common.response.CommonResponse;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 类目服务
 * Created by zhengzhiqing on 16/6/17.
 */
public interface CategoryService {

    /**
     * 创建类目
     *
     * @param category
     * @return
     */
    CommonResponse createCategory(Category category);
    /**
     * 更新类目
     *
     * @param category
     * @return
     */
    CommonResponse updateCategory(Category category);

    /**
     * 根据ID删除属性
     * @param categoryId
     * @return
     */
    CommonResponse deleteCategoryById(int categoryId);

    /**
     * 根据Id查询属性
     *
     * @param categoryId
     * @return
     */
    CategoryResponse getCategoryById(int categoryId);
    /**
     * 查询指定类目的子类目
     *
     * @param categoryId
     * @return
     */
    CategoryListResponse getSubCategory(int categoryId);

    /**
     * 查询指定类目的父类目
     *
     * @param categoryId
     * @return
     */
    CategoryResponse getParentCategory(int categoryId);

    /**
     * 查询所有一级类目
     *
     * @return
     */
    CategoryListResponse getFirstLevelCategory();

    /**
     * 查询指定分类所有三级分类(递归查询)
     *
     * @return
     */
    List<Integer> getLastLevelCategoryByCategory(int categoryId,Integer categoryLevel);
    
    /**
     * 查询二级分类
     *
     * @param categoryParendId
     * @return
     */
    public CategorySecondResponse getCategoryByParentId(Integer categoryParendId);
    
}
