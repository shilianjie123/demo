package com.example.school.study.demo.service.impl;

import com.example.school.study.demo.dao.LoginDao;
import com.example.school.study.demo.model.LoginInfo;
import com.example.school.study.demo.model.RegisterInfo;
import com.example.school.study.demo.model.vo.LoginUserVo;
import com.example.school.study.demo.service.LoginService;
import com.example.school.study.demo.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private RegisterService registerService;
    private static final Integer STUDENT = 1;
    private static final Integer TEACHER = 2;
    private static final Integer ADMIN = 3;

    /**
     *
     * 如果是学生 进入到答题页面
     * 如果是老师 进入上传视频和试题的页面
     * 如果是管理员则进入到后台页面
     * @param loginUserVo
     * @return
     */
    @Override
    public String login(LoginUserVo loginUserVo) {
        //通过userId查找出身份 然后去对应的表里面去查询出密码
        List<RegisterInfo> registerInfos = registerService.getInformationByRegister();
        List<Long> userIdList = registerInfos.stream()
                .map(RegisterInfo::getUserId)
                .collect(Collectors.toList());

        if (userIdList.contains(loginUserVo.getUserId())) {
            int identityStatus = registerInfos.stream()
                    .filter(t -> t.getUserId() == loginUserVo.getUserId())
                    .collect(Collectors.toList()).get(0).getUserIdentity();

            String password = null;
            if (identityStatus == STUDENT) {
                LoginInfo loginInfo = loginDao.getStudent(loginUserVo.getUserId());
                password = loginInfo.getPassword();
                if (password == loginUserVo.getPassword()) {
                    return "success";
                } else {
                    return "用户名或密码错误";
                }

            } else if (identityStatus == TEACHER) {
                LoginInfo loginInfo = loginDao.getTeacher(loginUserVo.getUserId());
                password = loginInfo.getPassword();
                if (password == loginUserVo.getPassword()) {
                    return "success";
                } else {
                    return "用户名或密码错误";
                }

            } else if (identityStatus == ADMIN) {
                LoginInfo loginInfo = loginDao.getAdmin(loginUserVo.getUserId());
                password = loginInfo.getPassword();
                if (password == loginUserVo.getPassword()) {
                    return "success";
                } else {
                    return "用户名或密码错误";
                }
            }
        } else {
            return "尚未注册";
        }
        return null;
    }

    @Override
    public LoginInfo getStudent(long userId) {
        return loginDao.getStudent(userId);
    }

    @Override
    public LoginInfo getTeacher(long userId) {
        return loginDao.getTeacher(userId);
    }

    @Override
    public LoginInfo getAdmin(long userId) {
        return loginDao.getAdmin(userId);
    }

    @Override
    public void addStudent(LoginInfo loginInfo) {
        loginDao.addStudent(loginInfo);
    }

    @Override
    public void addTeacher(LoginInfo loginInfo) {
        loginDao.addTeacher(loginInfo);
    }

    @Override
    public void addAdmin(LoginInfo loginInfo) {
        loginDao.addAdmin(loginInfo);
    }
}
