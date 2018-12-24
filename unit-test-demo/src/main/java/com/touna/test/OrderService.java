package com.touna.test;

import javax.annotation.Resource;

/**
 * @Author chenck
 * @Date 2018/12/12 16:54
 */
public class OrderService {

    // 用户服务(本地service)
    @Resource
    UserService userService;

    public Result<Boolean> submitOrderByDTO(OrderDTO orderDTO) {
        // 先校验用户身份
        if (!userService.checkUser(orderDTO.getUserId())) {
            // 用户身份不合法
            return new Result<Boolean>(false);
        }
        // 下单逻辑代码，
        // 省略...
        // 下单完成，给买家发邮件
        System.out.println("submitOrderByDTO:下单成功");
        return new Result<Boolean>(true);
    }
}
