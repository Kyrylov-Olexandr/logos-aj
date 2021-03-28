package com.kyrylov.web_app.service.impl;

import com.kyrylov.web_app.dao.UserDao;
import com.kyrylov.web_app.dao.impl.DefaultUserDao;
import com.kyrylov.web_app.model.UserModel;
import com.kyrylov.web_app.service.UserService;

public class DefaultUserService implements UserService {
    private UserDao userDao = new DefaultUserDao();
    @Override
    public boolean registerUser(UserModel userModel) {
        if(validate(userModel)) {
            userDao.save(userModel);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean loginUser(String email, String password) {
        UserModel userModel = userDao.getByEmail(email);
        if (userModel != null) {
            return userModel.getPassword().equals(password);
        }
        return false;
    }

    private boolean validate(UserModel userModel) {
        if (userDao.getByEmail(userModel.getEmail()) != null) {
            return false;
        }
        if (userModel.getPassword().length() < 10) {
            return false;
        }
        return true;
    }
}
