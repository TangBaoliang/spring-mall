package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.UserService;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class UserServiceImplTest {
    @Resource
    private UserService userService;
    @Test
    void register() {
        User user = new User();
        user.setPassword("1235464");
        user.setEmail("2425209089@qq.com");
        user.setUsername("唐宝亮");
        userService.register(user);;
    }
}