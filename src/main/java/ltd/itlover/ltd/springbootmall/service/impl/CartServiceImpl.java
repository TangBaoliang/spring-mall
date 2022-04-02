package ltd.itlover.ltd.springbootmall.service.impl;

import com.google.gson.Gson;
import io.netty.util.internal.StringUtil;
import ltd.itlover.ltd.springbootmall.enums.ProductStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.form.CartAddForm;
import ltd.itlover.ltd.springbootmall.form.CartUpdateForm;
import ltd.itlover.ltd.springbootmall.mapper.ProductMapper;
import ltd.itlover.ltd.springbootmall.pojo.Cart;
import ltd.itlover.ltd.springbootmall.pojo.Product;
import ltd.itlover.ltd.springbootmall.service.CartService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.CartProductVo;
import ltd.itlover.ltd.springbootmall.vo.CartVo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        Map<String, String> entries = opsForHash.entries(redisKey);
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        boolean selectAll = true;
        for (Map.Entry<String, String> stringStringEntry : entries.entrySet()) {
            Integer productId = Integer.valueOf(stringStringEntry.getKey());
            Cart cart = new Gson().fromJson(stringStringEntry.getValue(), Cart.class);
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVo cartProductVo = new CartProductVo(
                        productId,
                        cart.getQuantity(),
                        product.getSubtitle(),
                        product.getName(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                );
                cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                cartProductVoList.add(cartProductVo);
                if (!cart.getProductSelected()) {
                    selectAll = false;
                }
            }
        }
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartVo.getCartTotalQuantity());
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        return Result.success(cartVo);
    }

    @Override
    public Result update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, productId);
        Cart cart = null;
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            return Result.error(ResultCodeEnum.CART_PRODUCT_NOT_EXIST);
        }
        else {
            cart = new Gson().fromJson(value, Cart.class);
            if (cartUpdateForm.getQuantity() != null && cartUpdateForm.getQuantity() >= 0) {
                cart.setQuantity(cart.getQuantity());
            }
            if (cartUpdateForm.getSelected() != null) {
                cart.setProductSelected(cartUpdateForm.getSelected());
            }
            opsForHash.put(redisKey, String.valueOf(productId), new Gson().toJson(cart));


        }
        return list(uid);
    }

    @Override
    public Result delete(Integer userId, Integer productId) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, userId);
        String value = hashOperations.get(redisKey, String.valueOf(productId));
        if (value == null) {
            return Result.error(ResultCodeEnum.CART_PRODUCT_NOT_EXIST);
        }
        Long res = hashOperations.delete(redisKey, String.valueOf(productId));
        return list(userId);
    }
}
