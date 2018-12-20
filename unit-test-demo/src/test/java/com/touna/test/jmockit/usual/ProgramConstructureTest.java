package com.touna.test.jmockit.usual;

import com.touna.test.OrderFacadeImpl;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/*
 * Copyright (c) jmockit.cn
 * 访问JMockit中文网(jmockit.cn)了解该测试程序的细节
 */
//JMockit的程序结构
public class ProgramConstructureTest {
    long userId = 123456L;
    long productId = 456789L;

    // 这是一个测试属性
    @Mocked
    private OrderFacadeImpl orderFacadeImpl;

    @Test
    public void test1() {
        // 录制(Record)
        new Expectations() {// 这是一个匿名内部类
            {
                // 匿名内部类的初始化代码块，在这里录制脚本规范
                // 此处可以对外部类OrderFacadeImpl的任意成员变量、方法进行调用，方便我们编写录制脚本
                orderFacadeImpl.submitOrder(userId, productId);// 方法调用
                result = true;// 期待上述调用的返回是true
            }
        };
        // 重放(Replay)
//        Boolean msg = orderFacadeImpl.submitOrder(userId, productId);
//        Assert.assertEquals(true, msg);

        OrderFacadeImpl orderFacadeImpl = new OrderFacadeImpl();
        Boolean msg = orderFacadeImpl.submitOrder(userId, productId);
        Assert.assertEquals(true, msg);
        // 验证(Verification)
        new Verifications() {// 这是一个匿名内部类
            {
                // 匿名内部类的初始化代码块，在这里录制脚本规范
                // times/minTimes/maxTimes 表示调用次数的限定要求。
                // 赋值要紧跟在方法调用后面，也可以不写（表示只要调用过就行，不限次数）
                orderFacadeImpl.submitOrder(userId, productId);
                times = 1;
            }
        };
    }

    /* orderFacadeImpl 是一个测试参数 */
    @Ignore
    @Test
    public void test2(@Mocked final OrderFacadeImpl orderFacadeImpl ) {
        // 录制(Record)
        new Expectations() {
            {
                orderFacadeImpl.submitOrder(userId, productId);
                // 期待上述调用的返回是true
                result = true;
            }
        };
        // 重放(Replay)
        Boolean msg = orderFacadeImpl.submitOrder(userId, productId);
        Assert.assertEquals(true, msg);
        // 验证(Verification)
        new Verifications() {
            {
                orderFacadeImpl.submitOrder(userId, productId);
                times = 1;
            }
        };
    }
}
