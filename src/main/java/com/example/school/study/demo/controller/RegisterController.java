package com.example.school.study.demo.controller;

import com.example.school.study.demo.model.RegisterInfo;
import com.example.school.study.demo.model.vo.RegisterUserVo;
import com.example.school.study.demo.service.impl.RegisterServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {
    @Resource
    private RegisterServiceImpl registerService;

    @RequestMapping(value = "/send")
    public void registerUser(RegisterUserVo registerUserVo){

        registerService.registerUser(registerUserVo);
    }

    //获取所有注册信息 判断是否有当前学号  没有：返回报错 该用户尚未注册
    //如果有的话 再进行判断 学号id对应的密码和输入的密码是否一样 不一样 报错：用户名或者密码错误
    //判断无错误： 进入到下一个页面

    //如果是学生 进入到答题页面

    //如果是老师 进入上传视频和试题的页面

    //如果是管理员则进入到后台页面


}
