package com.kyrylov.web_app.dao.impl;

import com.kyrylov.web_app.dao.BucketDao;
import com.kyrylov.web_app.dao.ProductDao;
import com.kyrylov.web_app.dao.UserDao;
import com.kyrylov.web_app.model.BucketModel;
import com.kyrylov.web_app.model.ProductModel;
import com.kyrylov.web_app.model.UserModel;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.List;

import static com.kyrylov.web_app.utils.DatabaseUtils.getConnection;


public class DefaultBucketDao implements BucketDao {
    private static final String GET_BY_ID_QUERY = "select * from bucket where id = ?;";
    private static final String DELETE_BY_ID_QUERY = "delete from bucket where id = ?;";
    private static final String CREATE_QUERY = "insert into bucket(id, created_date) value (?, ?);";
    private static final String UPDATE_BY_ID_QUERY = "update bucket set id = ?, created_date = ? where id = ?;";
    private static final String GET_ALL_QUERY = "select * from bucket;";
    private static final String GET_ALL_BY_PRODUCT_ID_QUERY =
            "select * from bucket join bucket_product " +
            "on bucket.id = bucket_product.bucket_id " +
            "where bucket_project.project_id = ?;";

    private final ProductDao productDao = new DefaultProductDao();
    private final UserDao userDao = new DefaultUserDao();

    @SneakyThrows
    @Override
    public BucketModel getById(long id) {
        ResultSet resultSet = null;
        BucketModel result = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
        ) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = BucketModel
                        .builder()
                        .id(id)
                        .createdDate(resultSet.getTimestamp("created_date"))
                        .products(getAllProductsByBucketId(id))
                        .userModel(getUserByBucketId(id))
                        .build();
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return result;
    }

    @SneakyThrows
    @Override
    public boolean deleteById(long id) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID_QUERY);
        ) {
            statement.setLong(1, id);
            return statement.execute();
        }
    }

    @SneakyThrows
    @Override
    public boolean create(BucketModel bucketModel) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);
        ) {
            statement.setLong(1, bucketModel.getId());
            statement.setTimestamp(2, bucketModel.getCreatedDate());
            return statement.execute();
        }
    }

    @SneakyThrows
    @Override
    public boolean update(long id, BucketModel bucketModel) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_QUERY);
        ) {
            statement.setLong(3, id);
            statement.setLong(1, bucketModel.getId());
            statement.setTimestamp(2, bucketModel.getCreatedDate());
            return statement.execute();
        }
    }
    @SneakyThrows
    @Override
    public List<BucketModel> getAll() {
        List<BucketModel> result = null;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY)
        ) {
            while (resultSet.next()) {
                long bucketId = resultSet.getLong("id");
                result.add(BucketModel
                        .builder()
                        .id(bucketId)
                        .createdDate(resultSet.getTimestamp("created_date"))
                        .products(getAllProductsByBucketId(resultSet.getLong("id")))
                        .userModel(getUserByBucketId(bucketId))
                        .build());
            }
            return result;
        }
    }

    @SneakyThrows
    @Override
    public List<BucketModel> getAllByProductId(long productId) {
        ResultSet resultSet = null;
        List<BucketModel> result = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_PRODUCT_ID_QUERY);
        ) {
            statement.setLong(1, productId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long bucketId = resultSet.getLong("id");
                result.add(BucketModel
                        .builder()
                        .id(bucketId)
                        .createdDate(resultSet.getTimestamp("created_date"))
                        .products(getAllProductsByBucketId(bucketId))
                        .userModel(getUserByBucketId(bucketId))
                        .build());
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return result;
    }

    private List<ProductModel> getAllProductsByBucketId(long id) {
        return productDao.getAllByBucketId(id);
    }

    private UserModel getUserByBucketId(long id) {
        return userDao.getById(id);
    }

}
