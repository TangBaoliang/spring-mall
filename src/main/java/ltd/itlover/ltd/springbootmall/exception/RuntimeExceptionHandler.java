package ltd.itlover.ltd.springbootmall.exception;

import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Result handle1(RuntimeException e) {
        return Result.error(ResultCodeEnum.ERROR, e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handle2(UserLoginException e) {
        return Result.error(ResultCodeEnum.NEED_LOGIN, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result notValidException(MethodArgumentNotValidException e) {
        return Result.error(ResultCodeEnum.PARAMETER_ERROR, e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
