package com.example.school.study.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserVo {

    private String userName;
    private long userId;
    private String email;
    private String password;
    //班级 教研组之类的
    private String userClass;
    //注册码
    private long registerNumber;
    /**
     * 1:学生 2:老师 3:管理员
     */
    private Integer identity;
}
