package com.touna.test.jmockit.mockup;

import com.touna.test.MailFacade;
import mockit.Mock;
import mockit.MockUp;

/**
 * 邮件服务消费端 的MockUp(伪类）
 *
 * @Author chenck
 * @Date 2018/12/11 17:13
 */
public class MailFacadeConsumerMockUp extends MockUp<MailFacade> {

    // 在这里书写对这个消费bean进行mock的mock逻辑，想mock哪个方法，就自行添加，注意方法一定要加上@Mock注解哦
    @Mock
    public boolean sendMail(long userId, String content) {
        // 单元测试时，不需要调用邮件服务器发送邮件，这里统一mock邮件发送成功
        System.out.println("MailFacadeConsumerMockUp.sendMail return true");
        return true;
    }
}
