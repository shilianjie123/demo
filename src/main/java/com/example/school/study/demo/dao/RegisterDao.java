package com.example.school.study.demo.dao;

import com.example.school.study.demo.model.RegisterInfo;

import java.util.List;

public interface RegisterDao {

    void addRegister(RegisterInfo registerInfo);

    List<RegisterInfo> getInformationByRegister();
}
