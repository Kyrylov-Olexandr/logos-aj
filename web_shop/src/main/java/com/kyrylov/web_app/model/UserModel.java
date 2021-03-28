package com.kyrylov.web_app.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;

}
