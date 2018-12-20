package com.touna.test.springboot.controller;

import com.touna.test.springboot.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author chenck
 * @Date 2018/12/13 13:02
 */
@Controller
public class SampleController {

    @Resource
    UserService userService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
//        boolean result = userService.checkUser(000000);
        boolean result = userService.checkUser(123456L);
        System.out.println("checkUser = " + result);
        return "Hello World!";
    }

}