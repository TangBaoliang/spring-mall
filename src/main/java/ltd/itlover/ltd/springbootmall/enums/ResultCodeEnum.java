package ltd.itlover.ltd.springbootmall.enums;

import lombok.Getter;

/**
 * @author 宝亮
 */

@Getter
public enum ResultCodeEnum {
    SUCCESS(0, "成功"),
    ERROR(-1, "服务器错误"),
    PASSWORD_ERROR(1, "密码错误"),
    USER_EXIST(2, "用户已存在"),
    PARAMETER_ERROR(3, "参数错误"),
    NEED_LOGIN(10, "用户未登录，请登录"),
    ACCOUNT_NOT_EXIST(11, "该邮箱对应用户不存在"),
    PRODUCT_OFF_SALE_OR_DELETE(12, "商品不存在或者删除"),

    PRODUCT_NOT_EXIST(12, "商品不存在"),
    STOCK_NOT_ENOUGH(13, "商品库存不足"),
    DELETE_ERROR(15, "删除失败"),
    ADD_ERROR(16, "添加失败"),
    MODIFY_ERROR(18, "更新失败"),
    CONTACT_WAY_NULL(17, "联系方式不能为空"),
    CART_PRODUCT_NOT_EXIST(14, "购物车里无此商品"),
    ORDER_STATUS_ERROR(22, "订单状态错误"),
    MODIFY_ORDER_STATUS_FAILED(23, "修改订单状态失败"),
    ORDER_NOT_EXIST(21, "不存在的订单被支付了");


    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
