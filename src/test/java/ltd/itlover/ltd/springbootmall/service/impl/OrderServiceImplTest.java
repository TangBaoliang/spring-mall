package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Repeat;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TangBaoLiang
 * @date 2022/4/20
 * @email developert163@163.com
 **/
class OrderServiceImplTest extends ProductServiceImplTest{

    @Resource
    private OrderService orderService;

    @Test
    void paid() {
        orderService.paid(1650379301397L);
    }
}