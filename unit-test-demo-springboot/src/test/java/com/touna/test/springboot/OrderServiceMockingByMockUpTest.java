package com.touna.test.springboot;

import com.touna.test.springboot.mockup.UserServiceMockUp;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceMockingByMockUpTest {

    static long testUserId = 123456L;

    @Resource
    OrderService orderService;

    @Test
    public void testSubmitOrderByDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserId(111222);
        orderDTO.setUserId(testUserId);
        Result<Boolean> result = orderService.submitOrderByDTO(orderDTO);
        Assert.assertTrue(result.getData());
    }


    @BeforeClass
    public static void beforeClassMethods() throws Throwable {
        // 必须在Spring容器初始化前，就对Spring Bean的类做MockUp
        // 添加MockUp
        new UserServiceMockUp();
    }

}
