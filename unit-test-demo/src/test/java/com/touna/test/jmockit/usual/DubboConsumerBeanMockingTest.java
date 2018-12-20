package com.touna.test.jmockit.usual;

import com.touna.test.MailFacade;
import com.touna.test.OrderFacade;
import com.touna.test.jmockit.mockup.DubboConsumerBeanMockUp;
import com.touna.test.jmockit.mockup.MailFacadeConsumerMockUp;
import com.touna.test.jmockit.mockup.RocketMQProducerMockUp;
import com.touna.test.jmockit.mockup.UserServiceImplConsumerMockUp;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//dubbo消费bean Mock
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/META-INF/applicationContext2.xml"})
public class DubboConsumerBeanMockingTest {

    // 这里要@BeforeClass,因为要抢在spring加载dubbo前，对dubbo的消费工厂bean
    // ReferenceBean进行mock，不然dubbo可能因为连上不zk或无法找不
    // 服务的提供者等原因而无法初始化的，进而，单元测试运行不下去
    @BeforeClass
    public static void mockDubbo() {
        // 准备需要mock的消费bean（可加入多个mock类）
        Map<String, Object> mockMap = new HashMap<String, Object>();
        mockMap.put(MailFacade.class.getName(), (new MailFacadeConsumerMockUp()).getMockInstance());

        // 对dubbo的消费工厂bean ReferenceBean进行mock
        new DubboConsumerBeanMockUp(mockMap);

        // 对本地的UserServiceImpl进行mock
        new UserServiceImplConsumerMockUp();

        // 对MQ生产者进行mock
        new RocketMQProducerMockUp();
    }

    long testUserId = 123456L;
    long testProductId = 456789L;

    // ============================
    // 场景一：
    // 描述：直接使用dubbo消费bean(MailFacade)测试是否会使用Mock过的bean
    // 结果：使用本地mock过的bean，而不指向远程dubbo服务的bean
    @Resource
    MailFacade mailFacade;

    @Test
    public void testSendMail() {
        Assert.assertTrue(mailFacade.sendMail(testUserId, "test mail content"));
    }

    // ============================
    // 场景二：
    // 描述：订单facade中调用MailFacade是否会使用Mock过的bean
    // 结果：使用本地mock过的bean，而不指向远程dubbo服务的bean
    @Resource
    OrderFacade orderFacade;

    @Test
    public void testSubmitOrder() {
        Assert.assertTrue(orderFacade.submitOrder(testUserId, testProductId));
    }

}
