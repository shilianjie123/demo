package com.example.school.study.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo {

    private int id;
    private String userName;
    private String password;
    private long userId;
    private String userEmail;
    private String userClass;
}
