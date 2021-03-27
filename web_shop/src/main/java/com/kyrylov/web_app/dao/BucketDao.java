package com.kyrylov.web_app.dao;

import com.kyrylov.web_app.model.BucketModel;


import java.util.List;

public interface BucketDao {
    BucketModel getById(long id);

    boolean deleteById(long id);

    boolean create(BucketModel bucketModel);

    boolean update(long id, BucketModel bucketModel);

    List<BucketModel> getAll();

    List<BucketModel> getAllByProductId(long productId);
}
