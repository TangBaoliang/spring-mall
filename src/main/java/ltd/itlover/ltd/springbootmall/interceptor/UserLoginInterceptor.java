package ltd.itlover.ltd.springbootmall.interceptor;

import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User) request.getSession().getAttribute(MallConstance.CURRENT_USER);
        if (user == null) {
            log.info("user=null");
            throw new RuntimeException(ResultCodeEnum.NEED_LOGIN.getMsg());
        }
        return true;
    }
}
