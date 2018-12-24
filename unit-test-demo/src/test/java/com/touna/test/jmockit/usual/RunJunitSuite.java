package com.touna.test.jmockit.usual;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 测试套件 批量运行测试类的方法
 *
 * @Author chenck
 * @Date 2018/12/12 14:03
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        DeletgateResultTest.class,
        ClassMockingByMockUpTest.class,
        DubboConsumerBeanMockingTest.class
})
public class RunJunitSuite {

    // 注意：要保证测试套件之间没有循环包含关系，否则会出现无尽的循环
}
