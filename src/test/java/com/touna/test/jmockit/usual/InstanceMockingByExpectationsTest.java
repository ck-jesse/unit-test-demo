package com.touna.test.jmockit.usual;

import cn.jmockit.demos.AnOrdinaryClass;
import com.touna.test.JNITools;
import mockit.Expectations;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Copyright (c) jmockit.cn
 * 访问JMockit中文网(jmockit.cn)了解该测试程序的细节
 */
//mock实例
public class InstanceMockingByExpectationsTest {

    @Test
    public void testInstanceMockingByExpectation() {
        final AnOrdinaryClass instance = new AnOrdinaryClass();

        // 直接把实例传给Expectations的构造函数即可Mock这个实例
        new Expectations(instance) {
            {
                // 尽管这里也可以Mock静态方法，但不推荐在这里写。静态方法的Mock应该是针对类的
                // mock普通方法
                instance.ordinaryMethod();
                result = 21;
                // mock final方法
                instance.finalMethod();
                result = 31;
                // native, private方法无法用Expectations来Mock
            }
        };
        Assert.assertEquals(1, AnOrdinaryClass.staticMethod());
        Assert.assertEquals(21, instance.ordinaryMethod());
        Assert.assertEquals(31, instance.finalMethod());

        // 另一个实例
        // AnOrdinaryClass instance1 = new AnOrdinaryClass();
        // Assert.assertEquals(21, instance1.ordinaryMethod());
        // Assert.assertEquals(31, instance1.finalMethod());

        // 用Expectations无法mock native方法
        Assert.assertEquals(4, instance.navtiveMethod());
        // 用Expectations无法mock private方法
        Assert.assertEquals(5, instance.callPrivateMethod());
    }

    // 加载AnOrdinaryClass类的native方法的native实现
    @BeforeClass
    public static void loadNative() throws Throwable {
        JNITools.loadNative();
    }
}
