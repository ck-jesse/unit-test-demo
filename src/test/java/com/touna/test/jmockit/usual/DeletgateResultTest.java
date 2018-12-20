package com.touna.test.jmockit.usual;

import com.touna.test.OrderFacadeImpl;
import mockit.Delegate;
import mockit.Expectations;
import mockit.Invocation;
import org.junit.Test;
import org.springframework.util.Assert;

/*
 * Copyright (c) jmockit.cn 
 * 访问JMockit中文网(jmockit.cn)了解该测试程序的细节
 */
// 定制返回结果
public class DeletgateResultTest {

	static long testUserId = 123456L;
	static long testProductId = 456789L;

	@SuppressWarnings("rawtypes")
	@Test
	public void testDelegate() {
		new Expectations(OrderFacadeImpl.class) {
			{
				OrderFacadeImpl orderFacade = new OrderFacadeImpl();
				orderFacade.submitOrder(anyLong, anyLong);
				result = new Delegate() {
					// 当调用submitOrder(anyString, anyInt)时，返回的结果就会匹配delegate方法，
					// 方法名可以自定义，当入参和返回要与当调用submitOrder(anyString, anyInt)匹配上
					Boolean delegate111(Invocation inv, long userId, long productId) {
						// 指定值才走mock
						if (userId == testUserId) {
							System.out.println("当userId=" + testUserId + "时，submitOrder返回定制结果返回true");
							return true;
						}
						// 其它的入参，还是走原有的方法调用
						return inv.proceed(userId, productId);
					}
				};

			}
		};
		OrderFacadeImpl orderFacade = new OrderFacadeImpl();
		Assert.isTrue(orderFacade.submitOrder(testUserId, testProductId));
//		Assert.isTrue(orderFacade.submitOrder(123123L, productId));
	}
}
