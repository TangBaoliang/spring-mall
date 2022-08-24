package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.mapper.UserMapper;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.UserService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:application.yaml")
class UserServiceImplTest {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public User constructParameter(String email, String password, String username) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        return user;
    }

    /**
     * 预期抛出异常
     */
    @Test
    @DisplayName("已经存在的用户")
    void register_existed() {
        User user = constructParameter("2425@qq.com", "123456", "唐宝亮");
        userService.register(user);
        Assertions.assertThrows(RuntimeException.class, ()->{
            userService.register(user);
        }, "该Email已经被注册");
    }

    /**
     * 可成功注册的用户
     */
    @Test
    @DisplayName("不存在的正常可注册用户")
    void register_success() {
        User user = constructParameter("123123123@qq.com", "123456", "唐宝亮");
        Result result =  userService.register(user);
        assertThat(result.getMsg().equals("成功"));
    }

    /**
     * 传入一个 null 空值, 会爆出空指针异常，因为没有对空值做限制，所有的参数完整性限制全都做在 Controller 了
     */
    @Test
    @DisplayName("传入空值注册")
    void to_register_is_null () {
        Assertions.assertThrows(NullPointerException.class, ()->{
            userService.register(null);
        });
    }

    /**
     * 传入
     */
}