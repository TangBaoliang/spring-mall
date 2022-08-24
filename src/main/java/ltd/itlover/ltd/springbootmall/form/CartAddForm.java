package ltd.itlover.ltd.springbootmall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author TangBaoLiang
 * @date 2022/3/18
 * @email developert163@163.com
 **/
@Data
public class CartAddForm {
    @NotNull
    private Integer productId;

    private Boolean selected = true;

}
