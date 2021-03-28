package com.kyrylov.web_app.servlets;

import com.kyrylov.web_app.model.UserModel;
import com.kyrylov.web_app.service.UserService;
import com.kyrylov.web_app.service.impl.DefaultUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private UserService userService = new DefaultUserService();
    private AtomicLong ID_HOLDER = new AtomicLong(0);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserModel userModel = UserModel.builder()
                 .id(ID_HOLDER.incrementAndGet())
                 .firstName(req.getParameter("firstName"))
                 .lastName(req.getParameter("lastName"))
                 .email(req.getParameter("email"))
                 .password(req.getParameter("password"))
                 .build();

        if (userService.registerUser(userModel)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }

    }
}
