package com.touna.test.jmockit.usual;

import com.touna.test.UserService;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

//用MockUp来Mock Spring Bean
@ContextConfiguration(locations = {"/META-INF/applicationContext1.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBeanMockingByMockUpTest {

    // 注入Spring Bean，Mock这个实例，就达到了Mock Spring Bean的目的
    @Resource
    UserService userService;
    static long testUserId = 123456L;

    @Test
    public void testSpringBeanMockingByMockUp() {
        Assert.assertTrue(userService.checkUser(111222));
        Assert.assertTrue(userService.checkUser(testUserId));
        Assert.assertEquals("张三疯", userService.getUserName());
    }

    @BeforeClass
    public static void beforeClassMethods() throws Throwable {
        // 必须在Spring容器初始化前，就对Spring Bean的类做MockUp
        // 添加MockUp
        new UserServiceMockUp();
    }

    // 此处Mock实现类UserServiceImpl，而不是Mock接口UserService
    // public static class UserServiceMockUp extends MockUp<UserServiceImpl> {

    // 注：SpringBean 的Mock接口时，需要采用MockUp泛型的方式来实现
    public static class UserServiceMockUp<T extends UserService> extends MockUp<T> {
        @Mock
        public boolean checkUser(Invocation invocation, long userId) {
            // @Mock方法的参数满足特定条件时才走Mock逻辑
            if (userId == testUserId) {
                System.out.println("UserServiceMockUp.checkUser " + userId);
                return true;
            }
            // 其它条件走老的逻辑
            return invocation.proceed(userId);
        }

        @Mock
        public String getUserName() {
            System.out.println("UserServiceMockUp.getUserName");
            return "张三疯";
        }
    }
}
