package com.kyrylov.web_app.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductModel {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;

}
