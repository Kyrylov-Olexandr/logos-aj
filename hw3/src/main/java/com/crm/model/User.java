package com.crm.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
@Data
@Builder
public class User {
    int id;
    String firstName;
    String lastName;
    String city;
    String phone;
    String email;
    String password;
    Timestamp createdAt;


}
