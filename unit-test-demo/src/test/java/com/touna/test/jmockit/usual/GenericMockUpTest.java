package com.touna.test.jmockit.usual;

import com.touna.test.UserService;
import com.touna.test.UserServiceImpl;
import mockit.Capturing;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;

/*
 * Copyright (c) jmockit.cn
 * 访问JMockit中文网(jmockit.cn)了解该测试程序的细节
 */
//Mock泛型
public class GenericMockUpTest {

    // 自定义一个UserService的实现
    UserService instance1 = new UserService() {

        @Override
        public boolean checkUser(long userId) {
            return false;
        }

        @Override
        public long getUserId() {
            return 1111;
        }

        @Override
        public String getUserName() {
            return "user1";
        }
    };
    // 再定义一个UserService的实现
    UserService instance2 = new UserService() {
        @Override
        public boolean checkUser(long userId) {
            return false;
        }

        @Override
        public long getUserId() {
            return 2222;
        }

        @Override
        public String getUserName() {
            return "user2";
        }

    };
    // 再定义一个UserService的实现
    UserService instance3 = new UserServiceImpl();

    @Test
    public <T extends UserService> void testMockUp() {
        // 通过传给MockUp一个泛型类型变量，MockUp可以对这个类型变量的上限进行Mock，以后所有这个上限的方法调用，就会走Mock后的逻辑
        new MockUp<T>() {
            @Mock
            public boolean checkUser(long userId) {
                return true;
            }

            @Mock
            public long getUserId() {
                return 1111;
            }

            @Mock
            public String getUserName() {
                return "张三疯";
            }
        };

        // 发现自定义的实现没有起作用，而是被Mock逻辑替代了
        Assert.assertEquals(true, instance1.checkUser(11));
        Assert.assertEquals(true, instance2.checkUser(11));
        Assert.assertEquals(true, instance3.checkUser(11));
        Assert.assertEquals(1111, instance1.getUserId());
        Assert.assertEquals(1111, instance2.getUserId());
        Assert.assertEquals(1111, instance3.getUserId());
        Assert.assertEquals("张三疯", instance1.getUserName());
        Assert.assertEquals("张三疯", instance2.getUserName());
        Assert.assertEquals("张三疯", instance3.getUserName());


    }

    // 其实用@Capturing也是一样的效果
    @Test
    public <T extends UserService> void sameEffect(@Capturing final UserService instance) {
        new Expectations() {
            {
                instance.checkUser(anyLong);
                result = true;
                instance.getUserId();
                result = 1111;
                instance.getUserName();
                result = "张三疯";
            }
        };

        // 发现自定义的实现没有起作用，而是被Mock逻辑替代了
        Assert.assertEquals(true, instance1.checkUser(11));
        Assert.assertEquals(true, instance2.checkUser(11));
        Assert.assertEquals(true, instance3.checkUser(11));
        Assert.assertEquals(1111, instance1.getUserId());
        Assert.assertEquals(1111, instance2.getUserId());
        Assert.assertEquals(1111, instance3.getUserId());
        Assert.assertEquals("张三疯", instance1.getUserName());
        Assert.assertEquals("张三疯", instance2.getUserName());
        Assert.assertEquals("张三疯", instance3.getUserName());
    }
}
