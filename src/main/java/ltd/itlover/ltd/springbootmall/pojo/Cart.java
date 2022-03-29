package ltd.itlover.ltd.springbootmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/
@Data
@AllArgsConstructor
public class Cart {
    private Integer productId;
    private Integer quantity;
    private Boolean productSelected;

    public Cart() {

    }
}
