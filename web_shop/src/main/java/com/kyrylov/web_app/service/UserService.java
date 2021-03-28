package com.kyrylov.web_app.service;

import com.kyrylov.web_app.model.UserModel;

public interface UserService {

    boolean registerUser(UserModel userModel);

    boolean loginUser(String email, String password);
}
