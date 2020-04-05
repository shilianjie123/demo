package com.example.school.study.demo.controller;

import com.example.school.study.demo.model.vo.LoginUserVo;
import com.example.school.study.demo.service.LoginService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private LoginService loginService;

    @GetMapping(value = "/in")
    public String loginIn(LoginUserVo loginUserVo) {
        String html = loginService.login(loginUserVo);
        return html;
    }

    @GetMapping(value = "/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
