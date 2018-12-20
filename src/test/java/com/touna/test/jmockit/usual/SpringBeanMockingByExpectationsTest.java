package com.touna.test.jmockit.usual;

import com.touna.test.UserService;
import mockit.Expectations;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

//用MockUp来Mock Spring Bean
@ContextConfiguration(locations = {"/META-INF/applicationContext1.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBeanMockingByExpectationsTest {

    long userId = 123456L;
    long productId = 456789L;

    // 注入Spring Bean，Mock这个实例，就达到了Mock Spring Bean的目的
    @Resource
    UserService userService;

    @Test
    public void testCheckUser() {
        // 直接把实例传给Expectations的构造函数即可Mock这个实例
        new Expectations(userService) {
            {
                userService.checkUser(userId);
                result = true;
            }
        };
        Assert.assertTrue(userService.checkUser(userId));
    }
}
