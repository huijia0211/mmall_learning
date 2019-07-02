package com.mmall.service;

import com.mmall.common.ServerResponse;

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
}
