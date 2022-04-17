package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.enums.OrderStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.mapper.OrderMapper;
import ltd.itlover.ltd.springbootmall.pojo.Order;
import ltd.itlover.ltd.springbootmall.pojo.OrderExample;
import ltd.itlover.ltd.springbootmall.service.OrderService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author TangBaoLiang
 * @date 2022/4/17
 * @email developert163@163.com
 **/

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public void paid(Long orderNo) {
        //根据订单号查找订单
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if (orders == null && orders.size() <= 0) {
            throw new RuntimeException(ResultCodeEnum.ORDER_NOT_EXIST.getMsg() + "订单号：" + orderNo);
        }

        Order order = orders.get(0);

        if (!order.getStatus().equals(OrderStatusEnum.NO_PAID.getCode())) {
            throw new RuntimeException(ResultCodeEnum.ORDER_STATUS_ERROR.getMsg());
        }

        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setCloseTime(new Date());
        //修改订单状态
        int count = orderMapper.updateByExampleSelective(order, orderExample);

        if (count <= 0) {
            throw new RuntimeException(ResultCodeEnum.MODIFY_ORDER_STATUS_FAILED.getMsg());
        }

    }
}
