package ltd.itlover.ltd.springbootmall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.CartService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/
@RestController
@RequestMapping("/cart")
public class CartController {
    @Resource
    private CartService cartService;
    @PostMapping("/carts")
    @ApiOperation(value = "添加购物车", authorizations = { @Authorization(value="Authorization") })
    public Result add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return cartService.add(cartAddForm, userId);
    }
}
