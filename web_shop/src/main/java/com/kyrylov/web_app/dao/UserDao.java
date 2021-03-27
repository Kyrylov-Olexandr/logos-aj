package com.kyrylov.web_app.dao;

import com.kyrylov.web_app.model.BucketModel;
import com.kyrylov.web_app.model.UserModel;

import java.util.List;

public interface UserDao {
    UserModel getById(long id);

    boolean deleteById(long id);

    boolean create(UserModel userModel);

    boolean update(long id, UserModel userModel);

    List<UserModel> getAll();

}
