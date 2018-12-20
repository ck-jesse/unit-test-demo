package com.touna.test.springboot.mockup;

import com.touna.test.springboot.UserService;
import mockit.Invocation;
import mockit.Mock;
import mockit.MockUp;

/**
 * Mock泛型，达到Mock接口的目的
 *
 * @Author chenck
 * @Date 2018/12/13 15:26
 */
public class UserServiceMockUp<T extends UserService> extends MockUp<T> {

    static long testUserId = 123456L;

    @Mock
    public boolean checkUser(Invocation invocation, long userId) {
        if (userId == 000000) {
            throw new IllegalArgumentException("000000参数不合法");
        }
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