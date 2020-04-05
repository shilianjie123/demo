package com.example.school.study.demo.model.convert;

import com.example.school.study.demo.model.LoginInfo;
import com.example.school.study.demo.model.RegisterInfo;
import com.example.school.study.demo.model.vo.RegisterUserVo;

public class RegisterConvert {

    public LoginInfo createLoginInfoByRegisterVo(RegisterUserVo registerUserVo){
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserEmail(registerUserVo.getEmail());
        loginInfo.setPassword(registerUserVo.getPassword());
        loginInfo.setUserClass(registerUserVo.getUserClass());
        loginInfo.setUserId(registerUserVo.getUserId());
        loginInfo.setUserName(registerUserVo.getUserName());
        return loginInfo;
    }

    public RegisterInfo createRegisterInfoByRegister(RegisterUserVo registerUserVo){
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setUserId(registerUserVo.getUserId());
        registerInfo.setUserIdentity(registerUserVo.getIdentity());
        return registerInfo;
    }
}
