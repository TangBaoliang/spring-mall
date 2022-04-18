package ltd.itlover.ltd.springbootmall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DelShippingVo {
    @ApiModelProperty(value = "收获地址的id数组")
    List<Integer> shippingIds;
}
