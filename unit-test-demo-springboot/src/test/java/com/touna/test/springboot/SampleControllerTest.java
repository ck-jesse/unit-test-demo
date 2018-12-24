package com.touna.test.springboot;

import com.alibaba.fastjson.JSON;
import com.touna.test.springboot.mockup.UserServiceMockUp;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * @Author chenck
 * @Date 2018/12/13 15:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleControllerTest {

    static long testUserId = 123456L;
    private MockMvc mockMvc;

    // 注入WebApplicationContext
    @Autowired
    private WebApplicationContext wac;

    @Resource
    UserService userService;

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


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute() throws Exception {
        // 直接把实例传给Expectations的构造函数即可Mock这个实例
        /*new Expectations(userService) {
            {
                userService.checkUser(testUserId);
                // 方式一：直接返回结果
                result = true;
                // 方式二：定制返回结果
                *//*result = new Delegate() {
                    Boolean delegate(Invocation inv, long userId) {
                        // 指定值才走mock
                        if (userId == testUserId) {
                            System.out.println("当userId=" + testUserId + "时，checkUser返回定制结果返回true");
                            return true;
                        }
                        // 其它的入参，还是走原有的方法调用
                        return inv.proceed(userId);
                    }
                };*//*
            }
        };*/

        //thrown.expect(IllegalArgumentException.class);
        //thrown.expect(NestedServletException.class);// Sping将异常做了一次转换，所以期望的异常为该异常
        //thrown.expectMessage("000000参数不合法");


        //调用接口，传入添加的用户参数
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JSON.toJSONString(null));
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
