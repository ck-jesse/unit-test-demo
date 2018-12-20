package com.touna.test.springboot;

import org.springframework.stereotype.Component;

/**
 * 用户身份校验
 */
@Component
public class UserServiceImpl implements UserService {

    public boolean checkUser(long userId) {
        // 默认用户校验不通过
        if(userId == 111222){
            System.out.println("UserServiceImpl.checkUser when userId equals 111222 return true");
            return true;
        }
        System.out.println("UserServiceImpl.checkUser return false");
        return false;
    }

    @Override
    public long getUserId() {
        System.out.println("UserServiceImpl.getUserId return 0");
        return 0;
    }

    @Override
    public String getUserName() {
        System.out.println("UserServiceImpl.getUserName return null");
        return null;
    }
}