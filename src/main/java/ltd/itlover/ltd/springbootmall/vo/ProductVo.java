package ltd.itlover.ltd.springbootmall.vo;

import lombok.Data;
import ltd.itlover.ltd.springbootmall.config.InterceptorConfig;

import java.math.BigDecimal;

@Data
public class ProductVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subTitle;
    private String mainImage;
    private Integer status;
    private BigDecimal price;
}
