package com.touna.test.testng;

import com.touna.test.UserServiceImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * JUnit 4 与 TestNG 的对比 ： https://www.ibm.com/developerworks/cn/java/j-cq08296/index.html
 *
 * @Author chenck
 * @Date 2018/12/13 15:53
 */
public class TestngFirstTest {

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        Boolean result = userService.checkUser(111111);
        Assert.assertTrue(result);

    }
}
