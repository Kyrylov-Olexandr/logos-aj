package com.crm.impl;

import com.crm.model.User;
import com.crm.services.Dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDao implements Dao<User> {
    private final static String conUrl = "jdbc:mysql://localhost:3306/crm";
    private final static String conUser = "root";
    private final static String conPassword = "root";



    @Override
    public User get(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection con = DriverManager.getConnection(conUrl, conUser, conPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return User.builder()
                            .id(rs.getInt("id"))
                            .firstName(rs.getString("first_name"))
                            .lastName(rs.getString("last_name"))
                            .city(rs.getString("city"))
                            .phone(rs.getString("phone"))
                            .email(rs.getString("email"))
                            .createdAt(rs.getTimestamp("created_at"))
                            .password(rs.getString("password"))
                            .build();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(conUrl, conUser, conPassword);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                users.add(User.builder()
                        .id(rs.getInt("id"))
                        .firstName(rs.getString("first_name"))
                        .lastName(rs.getString("last_name"))
                        .city(rs.getString("city"))
                        .phone(rs.getString("phone"))
                        .email(rs.getString("email"))
                        .createdAt(rs.getTimestamp("created_at"))
                        .password(rs.getString("password"))
                        .build());
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users(first_name, last_name, city, phone, email, created_at, password) VALUE (?,?,?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(conUrl, conUser, conPassword);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "Bob");
            ps.setString(2, "Marley");
            ps.setString(3, "Kyiv");
            ps.setString(4, "098212312");
            ps.setString(5, "bobmarley@gmail.com");
            ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(new Date())));
            ps.setString(7, "123123123");
        }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        System.out.println(userDao.get(1));
    }
}
