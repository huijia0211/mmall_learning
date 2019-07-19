package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

/**
 * @author Admin
 */
public interface IProductService {

    /**
     * 新增或修改产品
     * @param product
     * @return
     */
    ServerResponse saveOrUpdateProduct(Product product);
}
