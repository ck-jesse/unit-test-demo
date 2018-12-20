package com.touna.test.springboot;

/**
 * 订单信息
 */
public class OrderDTO {

    private long userId;// 用户id
    private Long productId;// 商品id
    private String orderId;// 订单号


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
