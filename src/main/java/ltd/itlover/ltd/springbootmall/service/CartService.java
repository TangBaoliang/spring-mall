package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.form.CartUpdateForm;
import ltd.itlover.ltd.springbootmall.pojo.Cart;
import ltd.itlover.ltd.springbootmall.utils.Result;

import java.util.List;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/
public interface CartService {
    Result add(CartAddForm cartAddForm, Integer userId);
    Result list(Integer uid);
    Result update (Integer uid, Integer productId, CartUpdateForm cartUpdateForm);
    Result delete (Integer userId, Integer productId);
    Result selectAll(Integer userId);

    Result unSelectAll(Integer userId);

    Result sum(Integer userId);

    List<Cart> listForCart(Integer userId);
}
