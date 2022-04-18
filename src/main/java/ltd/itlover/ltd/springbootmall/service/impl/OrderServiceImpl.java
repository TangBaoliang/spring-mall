package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.enums.OrderStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.mapper.OrderMapper;
import ltd.itlover.ltd.springbootmall.mapper.ProductMapper;
import ltd.itlover.ltd.springbootmall.mapper.ShippingMapper;
import ltd.itlover.ltd.springbootmall.pojo.*;
import ltd.itlover.ltd.springbootmall.service.CartService;
import ltd.itlover.ltd.springbootmall.service.OrderService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Handler;
import java.util.stream.Collectors;

/**
 * @author TangBaoLiang
 * @date 2022/4/17
 * @email developert163@163.com
 **/

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private ShippingMapper shippingMapper;

    @Resource
    private CartService cartService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductMapper productMapper;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result create(Integer userId, Integer shippingId) {
        //检查收获地址是否存在且为该用户的收获地址
        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(shippingId);
        List<Shipping> shippingList = shippingMapper.selectByExample(shippingExample);
        if (shippingList == null || shippingList.size() <= 0) {
            return Result.error(ResultCodeEnum.ERROR, "收货地址错误");
        }

        //获取购物车的信息, 检验是否有选中的商品
        List<Cart> cartList = cartService.listForCart(userId).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return Result.error(ResultCodeEnum.ERROR, "购物车为空");
        }

        //购物车中所有选中的商品的 id
        List<Integer> cartProductIdSet = cartList.stream().map(Cart::getProductId).collect(Collectors.toList());
        //根绝 购物车中所有选中的商品的 id 将购物车中所有选中商品的详细信息都查出来
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andIdIn(cartProductIdSet);
        List<Product> productList = productMapper.selectByExample(productExample);

        for (Product product : productList) {

        }


        return null;
    }


}
