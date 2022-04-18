package ltd.itlover.ltd.springbootmall.enums;

import lombok.Getter;

/**
 * @author TangBaoLiang
 * @date 2022/4/17
 * @email developert163@163.com
 **/
@Getter
public enum OrderStatusEnum {
    CANCELED(0, "已取消"),
    NO_PAID(10, "未付款"),
    PAID(20, "已付款"),
    SHIPPED(40, "已发货"),
    SUCCESS(50,"交易成功"),
    CLOSED(60, "交易关闭");


    private int code;
    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }
}
