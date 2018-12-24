package com.touna.test.jmockit.usual;

import com.touna.test.MailFacade;
import com.touna.test.OrderFacade;
import com.touna.test.UserService;
import com.touna.test.jmockit.mockup.DubboConsumerBeanMockUp;
import com.touna.test.jmockit.mockup.MailFacadeConsumerMockUp;
import com.touna.test.jmockit.mockup.RocketMQProducerMockUp;
import com.touna.test.jmockit.mockup.UserServiceImplConsumerMockUp;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Invocation;
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
@SuppressWarnings({"unchecked", "rawtypes"})
@ContextConfiguration(locations = {"/META-INF/applicationContext2.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
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
        // 思路：如果需要对本地UserService接口采用MockUp方式进行mock的话，需要将mock对象放入的BeanFactory中，后续再作研究... ...
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

    // 注入Spring Bean，Mock这个实例，就达到了Mock Spring Bean的目的
    @Resource
    UserService userService;

    @Test
    public void testSubmitOrder() {
        new Expectations(orderFacade, userService) {
            {
                // 定制返回结果
                orderFacade.submitOrder(anyLong, anyLong);
                result = new Delegate() {
                    // 当调用submitOrder(anyString, anyInt)时，返回的结果就会匹配delegate方法，
                    // 方法名可以自定义，当入参和返回要与当调用submitOrder(anyString, anyInt)匹配上
                    Boolean delegate(Invocation inv, long userId, long productId) {
                        if (userId == testUserId) {
                            System.out.println("submitOrder 当userId=" + testUserId + "时，submitOrder返回定制结果返回true");
                            return true;
                        }
                        System.out.println("submitOrder 定制结果：进入原有方法调用");
                        // 其它的入参，还是走原有的方法调用
                        return inv.proceed(userId, productId);
                    }
                };

                // 定制返回结果
                userService.checkUser(anyLong);
                result = new Delegate() {
                    Boolean delegate(Invocation inv, long userId) {
                        if (userId == testUserId) {
                            System.out.println("checkUser 当userId=" + testUserId + "时，checkUser返回定制结果返回true");
                            return true;
                        }
                        System.out.println("checkUser 定制结果：进入原有方法调用");
                        // 其它的入参，还是走原有的方法调用
                        return inv.proceed(userId);
                    }
                };
            }
        };
        Assert.assertTrue(orderFacade.submitOrder(testUserId, testProductId));
        Assert.assertTrue(orderFacade.submitOrder(111222L, testProductId));
    }

}
