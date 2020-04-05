package com.example.school.study.demo.service;

import com.example.school.study.demo.model.LoginInfo;
import com.example.school.study.demo.model.vo.LoginUserVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LoginService {

    LoginInfo getStudent(long userId);

    LoginInfo getTeacher(long userId);

    LoginInfo getAdmin(long userId);

    void addStudent(LoginInfo loginInfo);

    void addTeacher(LoginInfo loginInfo);

    void addAdmin(LoginInfo loginInfo);

    String login(LoginUserVo LoginUserVo);

}
