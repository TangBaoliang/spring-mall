package ltd.itlover.ltd.springbootmall.vo;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


/**
 * 购物车的整体信息
 * @author 宝亮
 */
@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Integer cartTotalQuantity;
    private boolean selectedAll;
}
