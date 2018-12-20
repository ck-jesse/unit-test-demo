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
        ClassMockingByExpectationsTest.class,
        ClassMockingByMockUpTest.class,
        DeletgateResultTest.class,
        DubboConsumerBeanMockingTest.class,
        ExpectExceptionTest.class,
        GenericMockUpTest.class,
        InstanceMockingByExpectationsTest.class,
        InterfaceMockingByExpectationsTest.class,
        InterfaceMockingByMockUpTest.class,
        ProgramConstructureTest.class,
        RocetMQProducerMockingTest.class,
        SpringBeanMockingByExpectationsTest.class,
        SpringBeanMockingByMockUpTest.class
})
public class RunJunitSuite {

    // 注意：要保证测试套件之间没有循环包含关系，否则会出现无尽的循环

    // JMockit路径覆盖率，因为JMockit的路径覆盖率报告中，清晰了标注了类的方法有哪些执行路径，每一个执行路径正是我们单元测试的测试目标。
}
