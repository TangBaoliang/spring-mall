package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.utils.Result;

/**
 * @author TangBaoLiang
 * @date 2022/4/17
 * @email developert163@163.com
 **/
public interface OrderService {

    /**
     * 将订单状态改为已经支付的状态
     * @param orderNo 订单号
     */
    void paid(Long orderNo);

    /**
     * 根据购物车创建订单
     * @param userId 用户的 id
     * @param shippingId 收获地址 id
     * @return 返回创建好的订单，包括订单号
     */
    Result create(Integer userId, Integer shippingId);
}
