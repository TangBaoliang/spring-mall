package ltd.itlover.ltd.springbootmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.service.CartService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/

@Slf4j
class CartServiceImplTest extends ProductServiceImplTest{
    @Resource
    private CartService cartService;

    @Test
    void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setSelected(true);
        cartAddForm.setProductId(26);
        log.info(cartService.add(cartAddForm, 1) + "");

    }
}