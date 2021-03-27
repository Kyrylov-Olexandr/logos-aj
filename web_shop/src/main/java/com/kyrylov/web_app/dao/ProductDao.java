package com.kyrylov.web_app.dao;

import com.kyrylov.web_app.model.BucketModel;
import com.kyrylov.web_app.model.ProductModel;

import java.util.List;

public interface ProductDao {
    ProductModel getById(long id);

    boolean deleteById(long id);

    boolean create(ProductModel productModel);

    boolean update(long id, ProductModel productModel);

    List<ProductModel> getAll();

    List<ProductModel> getAllByBucketId(long id);
}
