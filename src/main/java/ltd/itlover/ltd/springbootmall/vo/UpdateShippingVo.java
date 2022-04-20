package ltd.itlover.ltd.springbootmall.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateShippingVo {

//    @NotBlank(message = "收获地址的 id 不能为空")
//    @ApiModelProperty(value = "收货地址 id")
    private Integer id;

    @NotBlank(message = "收货人不能为空")
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收货人固定电话")
    private String receiverPhone;

    @NotBlank(message = "电话号码不能为空")
    @ApiModelProperty(value = "收货人移动电话")
    private String receiverMobile;

    @NotBlank(message = "省份不能为空")
    @ApiModelProperty(value = "省份")
    private String receiverProvince;

    @NotBlank(message = "城市不能为空")
    @ApiModelProperty(value = "城市")
    private String receiverCity;

    @NotBlank(message = "收获区域不能为空")
    @ApiModelProperty(value = "地区")
    private String receiverDistrict;

    @NotBlank(message = "详细地址不能为空")
    @ApiModelProperty(value = "详细地址")
    private String receiverAddress;

    @ApiModelProperty(value = "邮编")
    private String receiverZip;

}
