package ltd.itlover.ltd.springbootmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import ltd.itlover.ltd.springbootmall.enums.OrderStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.PaymentTypeEnum;
import ltd.itlover.ltd.springbootmall.enums.ProductStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.mapper.OrderItemMapper;
import ltd.itlover.ltd.springbootmall.mapper.OrderMapper;
import ltd.itlover.ltd.springbootmall.mapper.ProductMapper;
import ltd.itlover.ltd.springbootmall.mapper.ShippingMapper;
import ltd.itlover.ltd.springbootmall.pojo.*;
import ltd.itlover.ltd.springbootmall.service.CartService;
import ltd.itlover.ltd.springbootmall.service.OrderService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.OrderItemVo;
import ltd.itlover.ltd.springbootmall.vo.OrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
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

    @Resource
    private OrderItemMapper orderItemMapper;

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
    @Transactional
    public Result create(Integer userId, Integer shippingId) {
        //检查收获地址是否存在且为该用户的收获地址
        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(shippingId);
        List<Shipping> shippingList = shippingMapper.selectByExample(shippingExample);
        if (shippingList == null || shippingList.size() <= 0) {
            return Result.error(ResultCodeEnum.ERROR, "收货地址错误");
        }
        Shipping shipping = shippingList.get(0);
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
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, product -> product));

        Long distributedSingleNo = generateOrderNo();
        List<OrderItem> orderItemsList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = productMap.get(cart.getProductId());

            //是否有商品
            if (product == null) {
                return Result.error(ResultCodeEnum.ERROR, "商品不存在, productId = " + cart.getProductId());
            }

            //商品的上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())) {
                return Result.error(ResultCodeEnum.ERROR, "商品不是在售状态");
            }

            //库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return Result.error(ResultCodeEnum.ERROR, "库存不足");
            }


            OrderItem orderItem = buildOrderItem(userId, distributedSingleNo, cart.getQuantity(), product);
            orderItemsList.add(orderItem);

            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row =productMapper.updateByPrimaryKey(product);
            if (row <= 0) {
                return Result.error(ResultCodeEnum.ERROR, "减库存失败，productId = " + product.getId());
            }
        }

        //计算总价，只计算选中的商品
        //生成订单，入库：order 和 orderItem.事务
        Order order = buildOrder(userId, distributedSingleNo, shipping, orderItemsList);

        int rowForOrder =orderMapper.insert(order);
        if (rowForOrder <= 0) {
            return Result.error(ResultCodeEnum.ERROR, "新增订单失败");
        }

        int rowForItemSize = orderItemMapper.batchInsert(orderItemsList);
        int orderItemSize = orderItemsList.size();
        if (rowForItemSize < orderItemSize) {
            return Result.error(ResultCodeEnum.ERROR, "订单明细插入失败");
        }
        //清空购物车
        for (Cart cart : cartList) {
            cartService.delete(userId, cart.getProductId());
        }

        //构造 orderVo
        OrderVo orderVo = buildOrderVo(order, orderItemsList);
        return Result.success(orderVo);
    }

    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItems) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        List<OrderItemVo> orderItemVoList = orderItems.stream()
                .map(e -> {
                    OrderItemVo orderItemVo = new OrderItemVo();
                    BeanUtils.copyProperties(e, orderItemVo);
                    return orderItemVo;
                }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(orderItemVoList);

        return orderVo;
    }

    //这里有一个冗余的 userId 字段
    private OrderItem buildOrderItem (Integer userId, Long orderNo, Integer quantity, Product product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderNo(orderNo);
        orderItem.setCreateTime(new Date());
        orderItem.setQuantity(quantity);
        orderItem.setUpdateTime(new Date());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setUserId(userId);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private Order buildOrder (Integer userId, Long orderNo, Shipping shipping, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setShipping(new Gson().toJson(shipping));
        order.setCreateTime(new Date());

        BigDecimal payment = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setUserId(userId);
        order.setShippingId(shipping.getId());
        order.setPayment(payment);
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAID.getCode());
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        return order;
    }

    @Override
    public Result list(Integer userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        //获取用户的所有订单
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId);
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        if (orderList == null || orderList.size() <= 0) {
            return Result.success(new PageInfo<>());
        }

        //获取所有的订单编号
        List<Long> orderNoList = orderList.stream().map(Order::getOrderNo).collect(Collectors.toList());

        //获取所有的订单明细
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOrderNoIn(orderNoList);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        Map<Long, List<OrderItem>> longListMap = orderItems.stream().collect(Collectors.groupingBy(OrderItem::getOrderNo));

        List<OrderVo> orderVoList = new ArrayList<>();

        for (Order order : orderList) {
            OrderVo orderVo = buildOrderVo(order, longListMap.get(order.getOrderNo()));
            orderVoList.add(orderVo);
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(orderVoList);
        return Result.success(pageInfo);
    }

    @Override
    public Result cancel(Integer userId, Long orderNo) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId).andOrderNoEqualTo(orderNo);
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        if (orderList == null || orderList.size() <= 0) {
            return Result.error(ResultCodeEnum.ERROR, "不存在该订单");
        }
        Order order = orderList.get(0);

        if (!OrderStatusEnum.NO_PAID.getCode().equals(order.getStatus())) {
            return Result.error(ResultCodeEnum.ERROR, "只有未付款的订单可以取消");
        }

        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            return Result.error(ResultCodeEnum.ERROR, "订单取消失败");
        }
        return Result.success();
    }

    @Override
    public Result detail(Integer userId, Long orderNo) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUserIdEqualTo(userId).andOrderNoEqualTo(orderNo);
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        if ( CollectionUtils.isEmpty(orderList) ) {
            return Result.error(ResultCodeEnum.ERROR, "该订单不存在userId = " + userId + ",orderNo=" + orderNo);
        }
        Order order = orderList.get(0);
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        OrderVo orderVo = buildOrderVo(order, orderItems);

        return Result.success(orderVo);
    }
}
