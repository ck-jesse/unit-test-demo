package com.touna.test.jmockit.usual;

import com.touna.test.SampleServlet;
import com.touna.test.jmockit.mockup.UserServiceMockUp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @Author chenck
 * @Date 2018/12/19 18:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration // 用于声明一个ApplicationContext集成测试加载WebApplicationContext
@ContextConfiguration(locations = {"/META-INF/applicationContext1-mvc.xml", "/META-INF/applicationContext1.xml"})
public class SampleControllerTest {

    private MockMvc mockMvc;

    // 注入WebApplicationContext
    @Autowired
    private WebApplicationContext wac;

    // 在测试开始前初始化工作
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @BeforeClass
    public static void beforeClassMethods() throws Throwable {
        // 必须在Spring容器初始化前，就对Spring Bean的类做MockUp
        // 添加MockUp
        new UserServiceMockUp();
    }

    @Test
    public void execute() throws Exception {

        //调用接口，传入添加的用户参数
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/home");
        request.param("userId", "123456");

        // TODO 注意：针对有Check Session的Controller方法该怎么进行单元测试，后续再研究并补充demo

        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确", status == 200);
        Assert.assertFalse("错误", status != 200);
        System.out.println("返回结果：" + status);
        System.out.println(content);
    }


    // TODO 该测试方法通不过，ServletContext未模拟出来，需研究一下springmvc的DispatcherServlet是怎么模拟的
    @Test
    public void servletTest() throws Exception {
        ServletContext servletContext = wac.getServletContext();
        ServletRegistration servletRegistration = servletContext.addServlet("sampleServlet", SampleServlet.class.getName());
        servletRegistration.addMapping(new String[]{"/sample"});

        //调用接口，传入添加的用户参数
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/sample");
        request.param("userId", "123456");

        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue("正确", status == 200);
        Assert.assertFalse("错误", status != 200);
        System.out.println("返回结果：" + status);
        System.out.println(content);
    }

}
