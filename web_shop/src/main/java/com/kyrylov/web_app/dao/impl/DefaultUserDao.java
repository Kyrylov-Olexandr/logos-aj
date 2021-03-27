package com.kyrylov.web_app.dao.impl;

import com.kyrylov.web_app.dao.BucketDao;
import com.kyrylov.web_app.dao.UserDao;
import com.kyrylov.web_app.model.BucketModel;
import com.kyrylov.web_app.model.UserModel;
import com.kyrylov.web_app.model.UserModel;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static com.kyrylov.web_app.utils.DatabaseUtils.getConnection;

public class DefaultUserDao implements UserDao {
    private static final String GET_BY_ID_QUERY = "select * from user where id = ?;";
    private static final String DELETE_BY_ID_QUERY = "delete from user where id = ?;";
    private static final String CREATE_QUERY = "insert into user(id, email, password, first_name, last_name, role) value (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_BY_ID_QUERY = "update user set id = ?, email = ?, password = ?, first_name = ?, last_name = ?, role = ? where id = ?;";
    private static final String GET_ALL_QUERY = "select * from user;";

    private final BucketDao bucketDao = new DefaultBucketDao();

    @SneakyThrows
    @Override
    public UserModel getById(long id) {
        ResultSet resultSet = null;
        UserModel result = null;
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID_QUERY);
        ) {
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = UserModel
                        .builder()
                        .id(id)
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .role(resultSet.getString("role"))
                        .bucketModel(getBucketByUserId(id))
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
    public boolean create(UserModel userModel) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);
        ) {
            statement.setLong(1, userModel.getId());
            statement.setString(2, userModel.getEmail());
            statement.setString(3, userModel.getPassword());
            statement.setString(4, userModel.getFirstName());
            statement.setString(5, userModel.getLastName());
            statement.setString(6, userModel.getRole());
            return statement.execute();
        }
    }

    @SneakyThrows
    @Override
    public boolean update(long id, UserModel userModel) {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_BY_ID_QUERY);
        ) {
            statement.setLong(7, id);
            statement.setLong(1, userModel.getId());
            statement.setString(2, userModel.getEmail());
            statement.setString(3, userModel.getPassword());
            statement.setString(4, userModel.getFirstName());
            statement.setString(5, userModel.getLastName());
            statement.setString(6, userModel.getRole());
            return statement.execute();
        }
    }
    @SneakyThrows
    @Override
    public List<UserModel> getAll() {
        List<UserModel> result = null;
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY)
        ) {
            while (resultSet.next()) {
                long userId = resultSet.getLong("id");
                result.add(UserModel
                        .builder()
                        .id(userId)
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .role(resultSet.getString("role"))
                        .bucketModel(getBucketByUserId(userId))
                        .build());
            }
            return result;
        }
    }

    private BucketModel getBucketByUserId(long userId) {
        return bucketDao.getById(userId);
    }
}
