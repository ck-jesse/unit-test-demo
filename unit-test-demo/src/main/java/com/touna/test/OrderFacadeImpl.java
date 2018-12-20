package com.touna.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单服务
 *
 * @Author chenck
 * @Date 2018/12/11 16:56
 */
@Service
public class OrderFacadeImpl implements OrderFacade {

    // 邮件服务(dubbo服务)
    @Resource
    MailFacade mailService;

    // 用户服务(本地service)
    @Resource
    UserService userService;

    // 消息生产者
    @Resource
    DefaultMQProducer mqProducer;

    @Override
    public Boolean submitOrder(long userId, long productId) {
        // 先校验用户身份
        if (!userService.checkUser(userId)) {
            // 用户身份不合法
            return false;
        }
        // 下单逻辑代码，
        // 省略...
        // 下单完成，给买家发邮件
        if (!this.mailService.sendMail(userId, "下单成功")) {
            // 邮件发送失败
            return false;
        }
        System.out.println("submitOrder:下单成功");

        try {
            // 下单成功，则发送MQ消息
            SendResult sendResult = mqProducer.send(new Message("unit_test_topic", "下单成功".getBytes()));
            System.out.println(JSON.toJSONString(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
