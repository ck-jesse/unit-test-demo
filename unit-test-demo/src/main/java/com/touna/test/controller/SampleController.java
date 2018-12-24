package com.touna.test.controller;

import com.touna.test.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用于验证jmockit+springmvc来进行Controller中对外暴露的rest http接口的测试
 *
 * @Author chenck
 * @Date 2018/12/13 13:02
 */
@Controller
public class SampleController {

    @Resource
    UserService userService;

    @RequestMapping("/home")
    @ResponseBody
    String home(long userId) {
        boolean result = userService.checkUser(userId);
        System.out.println("checkUser = " + result);
        return "Hello World!";
    }

}