package com.touna.test.mission;

import org.mission.invoke.annotation.WebInvoke;

/**
 * 用于做mission框架的单元测试
 *
 * @Author chenck
 * @Date 2018/12/20 14:31
 */
@WebInvoke(name = "MissionService", url = "/mission.do", impl = MissionServiceImpl.class)
public interface MissionService {

    public void testuser();
}
