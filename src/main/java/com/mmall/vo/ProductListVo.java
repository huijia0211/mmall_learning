package com.mmall.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductListVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;

    private String imgHost;

}
