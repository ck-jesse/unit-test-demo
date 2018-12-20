package com.touna.test.jmockit.usual;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.touna.test.jmockit.mockup.RocketMQProducerMockUp;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;
/*
 * Copyright (c) jmockit.cn
 * 访问JMockit中文网(jmockit.cn)了解该测试程序的细节
 */

//RocketMQ消息生产者 Mock 
public class RocetMQProducerMockingTest {

    // 把RocketMQ的生产者mock
    @BeforeClass
    public static void mockRocketMQ() {
        new RocketMQProducerMockUp();
    }

    @Test
    public void testSendRocketMQMessage() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer");
        producer.setNamesrvAddr("192.168.0.2:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("unit_test_topic", "TagA", ("Hello " + i).getBytes());
            // 因为mq生产者已经mock,所以消息并不会真正的发送，即使nameServer连不上，也不影响单元测试的运行
            SendResult sendResult = producer.send(msg);
            System.out.println(JSON.toJSONString(sendResult));
            Assert.isTrue(sendResult.getSendStatus() == SendStatus.SEND_OK);
            Assert.isTrue(sendResult.getMsgId() != null);
        }
        producer.shutdown();
    }
}
