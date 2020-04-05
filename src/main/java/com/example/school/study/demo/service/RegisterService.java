package com.example.school.study.demo.service;

import com.example.school.study.demo.model.RegisterInfo;
import com.example.school.study.demo.model.vo.RegisterUserVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RegisterService {

    void addRegister(RegisterUserVo registerUserVo);

    List<RegisterInfo> getInformationByRegister();


}
