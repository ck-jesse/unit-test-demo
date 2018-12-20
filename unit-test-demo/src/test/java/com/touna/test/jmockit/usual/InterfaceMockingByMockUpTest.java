package com.touna.test.jmockit.usual;

import com.touna.test.UserService;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Assert;
import org.junit.Test;

//用MockUp来mock接口
public class InterfaceMockingByMockUpTest {

	@Test
	public void testInterfaceMockingByMockUp() {
		// 手工通过MockUp创建这个接口的实例
		UserService userService = new MockUp<UserService>(UserService.class) {
			// 对方法Mock
			@Mock
			public long getUserId() {
				return 1001;
			}

			@Mock
			public String getUserName() {
				return "张三疯";
			}
		}.getMockInstance();

		Assert.assertEquals(1001, userService.getUserId());
		Assert.assertEquals("张三疯", userService.getUserName());
	}
}
