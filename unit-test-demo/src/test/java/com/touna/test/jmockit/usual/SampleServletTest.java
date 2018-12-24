package com.touna.test.jmockit.usual;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.touna.test.SampleServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Author chenck
 * @Date 2018/12/20 10:27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // 用于声明一个ApplicationContext集成测试加载WebApplicationContext
@ContextConfiguration(locations = {"/META-INF/applicationContext1-mvc.xml", "/META-INF/applicationContext1.xml"})
public class SampleServletTest {

    // 创建Servlet的运行环境
    public static ServletRunner servletRunner = new ServletRunner();
    public static ServletUnitClient servletUnitClient;

    // 注入WebApplicationContext
    @Autowired
    private WebApplicationContext wac;

    // 在测试开始前初始化工作
    @Before
    public void setup() {
        // 向环境中注册Servlet
        servletRunner.registerServlet("sample", SampleServlet.class.getName());

        // 创建访问Servlet的客户端
        servletUnitClient = servletRunner.newClient();
    }

    @Test
    public void execute() throws Exception {
        // 发送请求
        WebRequest request = new GetMethodWebRequest("http://localhost/sample");
        request.setParameter("username", "testuser");

        InvocationContext ic = servletUnitClient.newInvocation(request);

        SampleServlet sampleServlet = (SampleServlet) ic.getServlet();
        // 测试servlet的某个方法
        sampleServlet.setApplicationContext(wac);

        // 获得模拟服务器的信息
        WebResponse response = servletUnitClient.getResponse(request);
        String result = response.getText();
        System.out.println(result);
        // 断言
        Assert.assertTrue(result.equals("success"));

    }


}
