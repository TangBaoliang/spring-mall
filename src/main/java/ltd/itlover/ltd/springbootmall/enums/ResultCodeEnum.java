package ltd.itlover.ltd.springbootmall.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(0, "成功"),
    ERROR(-1, "服务器错误"),
    PASSWORD_ERROR(1, "密码错误"),
    USER_EXIST(2, "用户已存在"),
    PARAMETER_ERROR(3, "参数错误"),
    NEED_LOGIN(10, "用户未登录，请登录"),
    ACCOUNT_NOT_EXIST(11, "该邮箱对应用户不存在"),
    PRODUCT_OFF_SALE_OR_DELETE(11, "商品不存在或者删除");
    private int code;
    private String msg;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}