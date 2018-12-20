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
//用Expectations来mock类
public class ClassMockingByExpectationsTest {

    @Test
    public void testClassMockingByExpectation() {
        final AnOrdinaryClass instanceToRecord = new AnOrdinaryClass();
        new Expectations(AnOrdinaryClass.class) {
            {
                // mock静态方法
                AnOrdinaryClass.staticMethod();
                result = 11;
                // mock普通方法
                instanceToRecord.ordinaryMethod();
                result = 21;
                // mock final方法
                instanceToRecord.finalMethod();
                result = 31;
                // native, private方法无法用Expectations来Mock
            }
        };
        AnOrdinaryClass instance = new AnOrdinaryClass();
        Assert.assertEquals(11, AnOrdinaryClass.staticMethod());
        Assert.assertEquals(21, instance.ordinaryMethod());
        Assert.assertEquals(31, instance.finalMethod());

        // 用Expectations无法mock native方法
        System.out.println(instance.navtiveMethod());
        Assert.assertEquals(4, instance.navtiveMethod());

        // 用Expectations无法mock private方法
        Assert.assertEquals(5, instance.callPrivateMethod());
    }

    @BeforeClass
    // 加载AnOrdinaryClass类的native方法的native实现
    public static void loadNative() throws Throwable {
        JNITools.loadNative();
    }
}
