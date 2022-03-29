package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.utils.Result;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/
public interface CartService {
    Result add(CartAddForm cartAddForm, Integer userId);
    Result list(Integer uid);

}
