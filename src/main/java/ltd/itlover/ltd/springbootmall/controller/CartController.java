package ltd.itlover.ltd.springbootmall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.form.CartUpdateForm;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.CartService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/carts")
    @ApiOperation(value = "显示购物车", authorizations = { @Authorization(value="Authorization") })
    public Result list(HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return cartService.list(userId);
    }


    @PutMapping("/carts/{productId}")
    @ApiOperation(value = "更新购物车", authorizations = { @Authorization(value="Authorization") })
    public Result update(@PathVariable("productId")Integer productId, HttpSession httpSession,@RequestBody CartUpdateForm cartUpdateForm) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return cartService.update(userId, productId, cartUpdateForm);
    }

    @PutMapping("/carts/selectAll")
    @ApiOperation(value = "全选购物车", authorizations = { @Authorization(value="Authorization") })
    public Result selectAll(HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return cartService.selectAll(userId);
    }

    @PutMapping("/carts/unSelectAll")
    @ApiOperation(value = "全不选购物车", authorizations = { @Authorization(value="Authorization") })
    public Result unSelectAll(HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return cartService.unSelectAll(userId);
    }

    @GetMapping("/carts/products/sum")
    @ApiOperation(value = "返回购物车总数", authorizations = { @Authorization(value="Authorization") })
    public Result sum (HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        return cartService.sum(userId);
    }

}
