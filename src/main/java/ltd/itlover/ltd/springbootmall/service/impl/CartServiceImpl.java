package ltd.itlover.ltd.springbootmall.service.impl;

import com.google.gson.Gson;
import ltd.itlover.ltd.springbootmall.enums.ProductStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.mapper.ProductMapper;
import ltd.itlover.ltd.springbootmall.pojo.Cart;
import ltd.itlover.ltd.springbootmall.pojo.Product;
import ltd.itlover.ltd.springbootmall.service.CartService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/
@Service
public class CartServiceImpl implements CartService {
    private final static String CART_REDIS_KEY_TEMPLATE = "car_%d";

    @Resource
    private ProductMapper productMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result add(CartAddForm cartAddForm, Integer userId) {
        Integer quantity = 1;

        //商品是否存在和是否可售
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());

        if (product == null) {
            return Result.error(ResultCodeEnum.PRODUCT_NOT_EXIST);
        }
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return Result.error(ResultCodeEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        if (product.getStock() <= 0) {
            return Result.error(ResultCodeEnum.STOCK_NOT_ENOUGH);
        }

        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, userId);
        //先从redis 中读取

        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart = null;
        if (StringUtils.isEmpty(value)) {
            //没有该商品
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        }
        else {
            //已经有了，数量 +1
            cart = new Gson().fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + 1);
        }
        //写入到 Redis 中
        //一般用 String 类型作为 redishash 的键


        opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE, userId), String.valueOf(product.getId()), new Gson().toJson(cart));
        return Result.success();
    }

    @Override
    public Result list(Integer userId) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, userId);

        Map<String, String> entries = opsForHash.entries(redisKey);
        for (Map.Entry<String, String> stringStringEntry : entries.entrySet()) {
            Integer productId = Integer.valueOf(stringStringEntry.getKey());
        }
        return null;
    }
}
