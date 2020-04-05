package com.example.school.study.demo.dao;

import com.example.school.study.demo.model.LoginInfo;
import com.example.school.study.demo.model.RegisterInfo;

import java.util.List;

public interface LoginDao {

    LoginInfo getStudent(long userId);

    LoginInfo getTeacher(long userId);

    LoginInfo getAdmin(long userId);

    void addStudent(LoginInfo loginInfo);

    void addTeacher(LoginInfo loginInfo);

    void addAdmin(LoginInfo loginInfo);
}
