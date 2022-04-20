package ltd.itlover.ltd.springbootmall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.UserService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;


    @PostMapping("/user/register")
    @ApiOperation(value="用户注册", authorizations = { @Authorization(value="Authorization") })
    public Result register(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("注册提交的参数有错误，{}，{}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return Result.error(ResultCodeEnum.PARAMETER_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/user/login")
    @ApiOperation(value="用户登录", authorizations = { @Authorization(value="Authorization") })
    public Result login(@Valid @RequestBody UserLoginVo userLoginVo, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return Result.error(ResultCodeEnum.PARAMETER_ERROR, bindingResult.getFieldError().getDefaultMessage());
        }
        User user = new User();
        //BeanUtils.copyProperties(userLoginVo, user);
        user.setPassword(userLoginVo.getPassword());
        user.setEmail(userLoginVo.getUsername());
        Result result = userService.login(user);
        httpServletRequest.getSession().setAttribute(MallConstance.CURRENT_USER, result.getData());
        return result;
    }

    @GetMapping("/user")
    public Result<User> userinfo(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstance.CURRENT_USER);
        if (user == null) {
            return Result.error(ResultCodeEnum.NEED_LOGIN);
        }

        return Result.success(user);
    }

    @PostMapping("/user/logout")
    @ApiOperation(value="退出登录", authorizations = { @Authorization(value="Authorization") })
    public Result logout(HttpSession session) {
        session.removeAttribute(MallConstance.CURRENT_USER);
        return Result.success();
    }
}
