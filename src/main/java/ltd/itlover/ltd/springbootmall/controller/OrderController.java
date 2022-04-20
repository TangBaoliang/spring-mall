package ltd.itlover.ltd.springbootmall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.OrderService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/orders/{orderNo}")
    @ApiOperation(value = "订单详情", authorizations = { @Authorization(value="Authorization") })
    public Result detail (@ApiIgnore HttpSession httpSession,@ApiParam(value = "订单编号") @PathVariable("orderNo") Long orderNo) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return orderService.detail(userId, orderNo);
    }

    @PostMapping("/orders")
    @ApiOperation(value = "创建订单", authorizations = { @Authorization(value="Authorization") })
    public Result create (@ApiIgnore HttpSession httpSession, @ApiParam(value = "收货地址id") @PathVariable("shippingId") Integer shippingId) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return orderService.create(userId, shippingId);
    }

    @GetMapping("/orders")
    @ApiOperation(value = "显示用户所有订单", authorizations = { @Authorization(value="Authorization") })
    public Result list (@RequestParam Integer pageNum,
                        @RequestParam Integer pageSize,
                        @ApiIgnore HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return orderService.list(userId, pageNum, pageSize);
    }

    @PutMapping("/orders/{orderNo}")
    @ApiOperation(value = "取消订单", authorizations = { @Authorization(value="Authorization") })
    public Result cancel (@ApiParam(value = "订单编号") @PathVariable("orderNo") Long orderNo,
                          @ApiIgnore  HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return orderService.cancel(userId, orderNo);
    }
}
