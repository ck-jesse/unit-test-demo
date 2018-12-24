package com.touna.test;

import com.alibaba.fastjson.JSON;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author chenck
 * @Date 2018/12/20 10:21
 */
public class SampleServlet extends HttpServlet {

    private WebApplicationContext wac;

    public void setApplicationContext(WebApplicationContext wac) throws ServletException {
        this.wac = wac;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String protocol = req.getProtocol();
        System.out.println(protocol);

        Map<String, String[]> paramMap = req.getParameterMap();
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            System.out.println(param.getKey() + " " + JSON.toJSONString(param.getValue()));
        }

        resp.getOutputStream().print("success");


        UserService userService1 = wac.getBean(UserService.class);
        boolean flag = userService1.checkUser(12345);
        System.out.println(flag);

    }
}
