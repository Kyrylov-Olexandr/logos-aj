package com.crm.model;

import lombok.Builder;

import java.sql.Timestamp;
@Builder
public class Order {
    int id;
    String name;
    String description;
    Timestamp createdAt;
    int user_id;
}
