package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @author Admin
 */
public interface ICategoryService {
    /**
     * 增加分类
     *
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     * 修改品类名
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse updateCategory(Integer categoryId, String categoryName);

    /**
     * 查询品类
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 查询品类及其子类
     * @param categoryId
     * @return
     */
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);
}
