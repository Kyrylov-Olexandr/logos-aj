package com.kyrylov.web_app.dao.impl;

import com.kyrylov.web_app.dao.BucketDao;
import com.kyrylov.web_app.dao.ProductDao;
import com.kyrylov.web_app.model.BucketModel;
import com.kyrylov.web_app.model.ProductModel;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static com.kyrylov.web_app.utils.DatabaseUtils.getConnection;

public class DefaultProductDao implements ProductDao {
    private static final String GET_BY_ID_QUERY = "select * from product where id = ?;";
    private static final String DELETE_BY_ID_QUERY = "delete from product where id = ?;";
    private static final String CREATE_QUERY = "insert into product(id, name, description, price) value (?, ?, ?, ?);";
    private static final String UPDATE_BY_ID_QUERY = "update product set id = ?, name = ?, description = ?, price = ? where id = ?;";
    private static final String GET_ALL_QUERY = "select * from product;";
    private static final String GET_ALL_BY_BUCKET_ID_QUERY =
            "select from product join bucket_product " +
            "on product.id = bucket_product.product_id " +
            "where bucket_product.bucket_id = ?;";

    private final BucketDao bucketDao = new DefaultBucketDao();

    @SneakyThrows
    @Override
    public ProductModel getById(long id) {
        ResultSet resultSet = null;
        ProductModel result = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
        ) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = ProductModel
                        .builder()
                        .id(id)
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(BigDecimal.valueOf(resultSet.getDouble("price")))
                        .buckets(getAllBucketsByProductId(id))
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
    public boolean create(ProductModel productModel) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);
        ) {
            statement.setLong(1, productModel.getId());
            statement.setString(2, productModel.getName());
            statement.setString(3, productModel.getDescription());
            statement.setLong(4, productModel.getPrice().longValue());
            return statement.execute();
        }
    }

    @SneakyThrows
    @Override
    public boolean update(long id, ProductModel productModel) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_QUERY);
        ) {
            statement.setLong(5, id);
            statement.setLong(1, productModel.getId());
            statement.setString(2, productModel.getName());
            statement.setString(3, productModel.getDescription());
            statement.setLong(4, productModel.getPrice().longValue());
            return statement.execute();
        }
    }
    @SneakyThrows
    @Override
    public List<ProductModel> getAll() {
        List<ProductModel> result = null;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY)
        ) {
            while (resultSet.next()) {
                long productId = resultSet.getLong("id");
                result.add(ProductModel
                        .builder()
                        .id(productId)
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(BigDecimal.valueOf(resultSet.getLong("price")))
                        .buckets(getAllBucketsByProductId(productId))
                        .build());
            }
            return result;
        }
    }

    @SneakyThrows
    @Override
    public List<ProductModel> getAllByBucketId(long id) {
        ResultSet resultSet = null;
        List<ProductModel> result = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_ALL_BY_BUCKET_ID_QUERY);
        ) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long productId = resultSet.getLong("id");
                result.add(ProductModel
                        .builder()
                        .id(productId)
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .price(resultSet.getBigDecimal("price"))
                        .buckets(getAllBucketsByProductId(productId))
                        .build());
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return result;
    }

    private List<BucketModel> getAllBucketsByProductId(long id) {
        return bucketDao.getAllByProductId(id);
    }
}
