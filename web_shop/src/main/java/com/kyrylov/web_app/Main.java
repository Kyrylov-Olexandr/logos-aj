package com.kyrylov.web_app;

import com.kyrylov.web_app.dao.UserDao;
import com.kyrylov.web_app.dao.impl.DefaultUserDao;
import com.kyrylov.web_app.model.UserModel;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new DefaultUserDao();
        UserModel user = UserModel
                .builder()
                .id(1)
                .email("example@example.com")
                .password("123pass")
                .firstName("John")
                .lastName("Cina")
                .role("USER")
                .build();
        userDao.create(user);
        System.out.println(userDao.getById(1));
    }
}
