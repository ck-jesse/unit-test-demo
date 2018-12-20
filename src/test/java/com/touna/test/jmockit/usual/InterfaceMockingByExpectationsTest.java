package com.touna.test.jmockit.usual;

import com.touna.test.OrderDTO;
import com.touna.test.OrderService;
import com.touna.test.Result;
import com.touna.test.UserService;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

//用Expectations来mock接口
public class InterfaceMockingByExpectationsTest {

    // 待测试的对象
    // 如果该对象没有赋值，JMockit会去实例化它，若@Tested的构造函数有参数，
    // 则JMockit通过在测试属性&测试参数中查找@Injectable修饰的Mocked对象注入@Tested对象的构造函数来实例化，
    // 不然，则用无参构造函数来实例化。除了构造函数的注入，JMockit还会通过属性查找的方式，把@Injectable对象注入到@Tested对象中。
    @Tested
    OrderService orderService;

    // 通过@Injectable，让JMockit帮我们生成这个接口的实例，
    // 一般来说，接口是给类来依赖的，我们给待测试的类加上@Tested，就可以让JMockit做依赖注入。详细见JMockit基础的章节
    @Injectable
    UserService userService;

    // 直接使用mock类进行测试
    @Test
    public void testInterfaceMockingByExpectation() {
        // 录制
        new Expectations() {
            {
                userService.getUserId();
                result = 1001;
                userService.getUserName();
                result = "张三疯";
            }
        };
        Assert.assertEquals(1001, userService.getUserId());
        Assert.assertEquals("张三疯", userService.getUserName());

    }

    // 通过JMockit注入
    @Test
    public void testSubmitOrderByDTO() {
        // 录制
        new Expectations() {
            {
                userService.checkUser(anyLong);
                result = true;
            }
        };
        OrderDTO orderDTO = new OrderDTO();
        Result<Boolean> result = orderService.submitOrderByDTO(orderDTO);
        Assert.assertTrue(result.getData());
    }
}
