package ltd.itlover.ltd.springbootmall.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    ON_SALE(1, "在售"),
    OFF_SALE(2, "已下架"),
    DELETED(3, "已删除");

    Integer code;
    String msg;
    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
