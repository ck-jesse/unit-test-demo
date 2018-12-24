package com.touna.test.mission;

import com.touna.test.UserService;
import com.touna.test.UserServiceImpl;
import org.mission.invoke.HttpInvokeHelper;

/**
 * @Author chenck
 * @Date 2018/12/20 14:32
 */
public class MissionServiceImpl implements MissionService {


    @Override
    public void testuser() {
        System.out.println("mission test");

        UserService userService = new UserServiceImpl();
        System.out.println(userService.checkUser(123456));

        HttpInvokeHelper.setInvokeResult("success");
    }


}
