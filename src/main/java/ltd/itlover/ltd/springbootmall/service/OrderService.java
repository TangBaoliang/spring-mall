package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.utils.Result;

/**
 * @author TangBaoLiang
 * @date 2022/4/17
 * @email developert163@163.com
 **/
public interface OrderService {

    /**
     *
     * TODO 更新订单的支付状态
     * @return
     */
    void paid(Long orderNo);
}
