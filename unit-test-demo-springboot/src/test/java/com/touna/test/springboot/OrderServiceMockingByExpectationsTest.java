package com.touna.test.springboot;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Invocation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceMockingByExpectationsTest {

    static long testUserId = 123456L;

    @Resource
    OrderService orderService;

    @Resource
    UserService userService;

    @Test
    public void testSubmitOrderByDTO() {
        // 直接把实例传给Expectations的构造函数即可Mock这个实例
        new Expectations(userService) {
            {
                userService.checkUser(testUserId);
                // 方式一：直接返回结果
                // result = true;
                // 方式二：定制返回结果
                result = new Delegate() {
                    Boolean delegate(Invocation inv, long userId) {
                        // 指定值才走mock
                        if (userId == testUserId) {
                            System.out.println("当userId=" + testUserId + "时，checkUser返回定制结果返回true");
                            return true;
                        }
                        // 其它的入参，还是走原有的方法调用
                        return inv.proceed(userId);
                    }
                };
            }
        };
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(111222);
        orderDTO.setUserId(testUserId);
        Result<Boolean> result = orderService.submitOrderByDTO(orderDTO);
        Assert.assertTrue(result.getData());
    }

}
