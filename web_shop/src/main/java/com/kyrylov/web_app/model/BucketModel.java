package com.kyrylov.web_app.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class BucketModel {
    private long id;
    private Timestamp createdDate;
    private UserModel userModel;
    private List<ProductModel> products;
}
