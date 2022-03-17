package ltd.itlover.ltd.springbootmall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
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
