package ltd.itlover.ltd.springbootmall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车每一项商品的信息
 * @author 宝亮
 */

@Data
@AllArgsConstructor
public class CartProductVo {
    private Integer productId;
    private Integer quantity;
    private String productSubTitle;
    private String productName;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private boolean productSelected;
}
