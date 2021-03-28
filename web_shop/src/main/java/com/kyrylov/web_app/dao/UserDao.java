package com.kyrylov.web_app.dao;

import com.kyrylov.web_app.model.BucketModel;
import com.kyrylov.web_app.model.UserModel;

import java.util.List;

public interface UserDao {
    UserModel getById(long id);

    UserModel getByEmail(String email);

    boolean deleteById(long id);

    boolean save(UserModel userModel);

    boolean update(long id, UserModel userModel);

    List<UserModel> getAll();


}
