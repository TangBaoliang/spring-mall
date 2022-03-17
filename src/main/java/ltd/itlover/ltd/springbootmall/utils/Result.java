package ltd.itlover.ltd.springbootmall.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;

@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Result<T> {
    private Integer status;
    private String msg;
    private T data;

    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg());
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), data);
    }
    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum) {
        return new Result<>(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }
    public static <T> Result<T> error(ResultCodeEnum resultCodeEnum, String msg) {
        return new Result<>(resultCodeEnum.getCode(), msg);
    }
}
