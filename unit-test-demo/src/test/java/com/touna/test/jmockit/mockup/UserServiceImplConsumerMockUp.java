package com.touna.test.jmockit.mockup;

import com.touna.test.UserServiceImpl;
import mockit.Mock;
import mockit.MockUp;

/**
 * 用户服务消费端 的MockUp(伪类）
 *
 * @Author chenck
 * @Date 2018/12/11 17:13
 */
public class UserServiceImplConsumerMockUp extends MockUp<UserServiceImpl> {

    // 在这里书写对这个消费bean进行mock的mock逻辑，想mock哪个方法，就自行添加，注意方法一定要加上@Mock注解哦
    @Mock
    public boolean checkUser(long userId) {
        System.out.println("UserServiceImplConsumerMockUp.checkUser return true");
        return true;
    }
}
