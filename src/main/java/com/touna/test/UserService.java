package com.touna.test;

/**
 * 用户身份校验
 */
public interface UserService {

    /**
     * 校验某个用户是否是合法用户
     *
     * @param userId 用户ID
     * @return 合法的就返回true, 否则返回false
     */
    public boolean checkUser(long userId);

    public long getUserId();

    public String getUserName();
}