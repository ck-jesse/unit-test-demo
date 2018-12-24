package com.touna.test.jmockit.usual;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.touna.test.jmockit.mockup.UserServiceMockUp;
import com.touna.test.mission.CustomJSONInvokeServlet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mission.invoke.InvokeManager;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Hashtable;

/**
 * @Author chenck
 * @Date 2018/12/20 10:27
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration // 用于声明一个ApplicationContext集成测试加载WebApplicationContext
//@ContextConfiguration(locations = {"/META-INF/applicationContext1-mvc.xml", "/META-INF/applicationContext1.xml"})
public class MissionServletTest {

    // 创建Servlet的运行环境
    public static ServletRunner servletRunner = new ServletRunner();
    public static ServletUnitClient servletUnitClient;

    // 在测试开始前初始化工作
    @Before
    public void setup() {
        // 向环境中注册Servlet
        // 设置ServletConfig中InitParameter，以便JSONInvokeServlet找到对应的service去执行
        Hashtable initParameters = new Hashtable();
        initParameters.put("service", "MissionService");
        // 此处对misssion中的JSONInvokeServlet进行扩展，只是注释掉 httpResponse.getStatus() 的判断逻辑
        // servletRunner.registerServlet("mission", JSONInvokeServlet.class.getName(), initParameters);
        servletRunner.registerServlet("mission", CustomJSONInvokeServlet.class.getName(), initParameters);

        // 创建访问Servlet的客户端
        servletUnitClient = servletRunner.newClient();
    }

    @BeforeClass
    public static void beforeClassMethods() throws Throwable {
        // 必须在Spring容器初始化前，就对Spring Bean的类做MockUp
        // 添加MockUp
        new UserServiceMockUp();
    }

    @Test
    public void test() throws IOException, SAXException {

        // mission框架从services.xml中加载service信息
        InvokeManager.start();

        WebRequest request = new GetMethodWebRequest("http://localhost/mission");
        request.setParameter("subtime", "123123123");
        request.setParameter("method", "testuser");
        request.setParameter("debug", "true");// 设置为debug模式，不check session
        // 获得模拟服务器的信息
        WebResponse response = servletUnitClient.getResponse(request);
        String result = response.getText();
        System.out.println(result);
    }

}
