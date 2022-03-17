package ltd.itlover.ltd.springbootmall.exception;

import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result handle(RuntimeException e) {
        return Result.error(ResultCodeEnum.ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handle(UserLoginException e) {
        return Result.error(ResultCodeEnum.NEED_LOGIN, e.getMessage());
    }
}
