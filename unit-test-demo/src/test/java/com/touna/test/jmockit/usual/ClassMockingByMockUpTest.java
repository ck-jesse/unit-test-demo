package com.touna.test.jmockit.usual;

import cn.jmockit.demos.AnOrdinaryClass;
import com.touna.test.JNITools;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * Copyright (c) jmockit.cn
 * 访问JMockit中文网(jmockit.cn)了解该测试程序的细节
 */
//用MockUp来mock类
public class ClassMockingByMockUpTest {

    // AnOrdinaryClass的MockUp类，继承MockUp即可
    public static class AnOrdinaryClassMockUp extends MockUp<AnOrdinaryClass> {
        // Mock静态方法
        @Mock
        public static int staticMethod() {
            System.out.println("Mock静态方法");
            return 11;
        }

        // Mock普通方法
        @Mock
        public int ordinaryMethod() {
            System.out.println("Mock普通方法");
            return 21;
        }

        @Mock
        // Mock final方法
        public final int finalMethod() {
            System.out.println("Mock final方法");
            return 31;
        }

        // Mock native方法
        @Mock
        public int navtiveMethod() {
            System.out.println("Mock native方法");
            return 41;
        }

        // Mock private方法
        @Mock
        private int privateMethod() {
            System.out.println("Mock private方法");
            return 51;
        }
    }

    @Test
    public void testClassMockingByMockUp() {

        new AnOrdinaryClassMockUp();

        AnOrdinaryClass instance = new AnOrdinaryClass();

        // 静态方法被mock了
        Assert.assertEquals(11, AnOrdinaryClass.staticMethod());

        // 普通方法被mock了
        Assert.assertEquals(21, instance.ordinaryMethod());

        // final方法被mock了
        Assert.assertEquals(31, instance.finalMethod());

        // native方法被mock了
        Assert.assertEquals(41, instance.navtiveMethod());

        // private方法被mock了
        Assert.assertEquals(51, instance.callPrivateMethod());
    }

    @BeforeClass
    // 加载AnOrdinaryClass类的native方法的native实现
    public static void loadNative() throws Throwable {
        JNITools.loadNative();
    }
}
