package com.example.school.study.demo.service.impl;

import com.example.school.study.demo.dao.RegisterDao;
import com.example.school.study.demo.model.RegisterInfo;
import com.example.school.study.demo.model.convert.RegisterConvert;
import com.example.school.study.demo.model.vo.RegisterUserVo;
import com.example.school.study.demo.service.LoginService;
import com.example.school.study.demo.service.RegisterService;
import com.example.school.study.demo.util.RandomNumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 1.注册：用户输入用户名 密码 和密码验证 还有注册码 班级姓名 邮箱  专业  学号 。。。
 * 2。点击发送验证码 然后根据接收到的邮箱发送验证码到该邮箱
 * 3。用户收到验证码后 把验证码输入  点击注册 后端进行判定 若验证码没有问题 那么就把该信息存入数据库
 */
@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final static String PRE = "pre_";

    @Value("${spring.mail.username}")
    private String from;
    @Resource
    private JavaMailSender mailSender;
    @Value("${register.subject}")
    private String subject;
    @Value("${register.contentV1}")
    private String contentV1;
    @Value("${register.contentV2}")
    private String contentV2;
    @Autowired
    private RedisTemplate<String, String> rt;
    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private LoginService loginService;

    RegisterConvert convert = new RegisterConvert();

    /**
     * 发送邮件
     *
     * @param sendTo
     * @param subject
     * @param content
     */
    public void sendSimpleMail(String sendTo, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
        try {
            mailSender.send(message);
            logger.info("邮件已经发送出去");
        } catch (Exception e) {
            logger.error("邮件没有发送出去" + e);
        }
    }

    /**
     * 发送验证码
     *
     * @param registerUserVo
     */
    public void registerUser(RegisterUserVo registerUserVo) {
        //拼接key
        String key = PRE + registerUserVo.getUserId();
        String ranNumber = null;
        //判断key是否存在
        boolean isExist = rt.hasKey(key);
        //如果存在从redis拿取已有的
        if (isExist) {
            ranNumber = rt.opsForValue().get(key);
            //邮箱发送
            sendSimpleMail(registerUserVo.getEmail(), subject, contentV2 + ranNumber);
        } else {
            //生成随机数
            ranNumber = RandomNumberUtil.getRandom();
            //邮箱发送
            sendSimpleMail(registerUserVo.getEmail(), subject, contentV1 + ranNumber);
            ValueOperations forValue = rt.opsForValue();
            forValue.set(key, ranNumber);
            rt.expire(key, 120, TimeUnit.SECONDS);
        }
    }

    /**
     * 注册信息
     *
     * @param registerUserVo
     * @return
     */
    public String registerSuccess(RegisterUserVo registerUserVo) {
        ValueOperations forValue = rt.opsForValue();
        String oldKey = (String) forValue.get(PRE + registerUserVo.getRegisterNumber());
        if (Objects.isNull(oldKey)) {
            return "验证码过期或不存在";
        } else if (oldKey.equals(registerUserVo.getRegisterNumber())) {

            List<RegisterInfo> registerInfos = getInformationByRegister();
            List<Long> userIdList = registerInfos.stream().map(RegisterInfo::getUserId).collect(Collectors.toList());
            if (userIdList.contains(registerUserVo.getUserId())) {
                return "该用户已经注册，请直接登录";
            } else {
                //将信息存入注册表
                registerDao.addRegister(convert.createRegisterInfoByRegister(registerUserVo));
                //将数据存入登录信息表
                if (registerUserVo.getIdentity() == 1) {
                    loginService.addStudent(convert.createLoginInfoByRegisterVo(registerUserVo));
                    //向学生表中插入数据
                } else if (registerUserVo.getIdentity() == 2) {
                    //向老师表里面插入数据
                    loginService.addTeacher(convert.createLoginInfoByRegisterVo(registerUserVo));
                }
                return "success";
            }
        } else {
            return "验证码错误";
        }
    }

    @Override
    public void addRegister(RegisterUserVo registerUserVo) {
        registerDao.addRegister(convert.createRegisterInfoByRegister(registerUserVo));
    }

    @Override
    public List<RegisterInfo> getInformationByRegister() {
        return registerDao.getInformationByRegister();
    }


}
