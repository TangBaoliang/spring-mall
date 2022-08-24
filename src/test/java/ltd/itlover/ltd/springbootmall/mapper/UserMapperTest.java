package ltd.itlover.ltd.springbootmall.mapper;

import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.pojo.UserExample;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author TangBaoLiang
 * @date 2022/5/29
 * @email developert163@163.com
 **/
@SpringBootTest
@Transactional
@TestPropertySource("classpath:application.yaml")
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    public User constructParameter(String email, String password, String username) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        return user;
    }

    @Test
    public void there_is_none_in_repository() {
        assert userMapper.selectByExample(new UserExample()).size() == 0;
    }

    @Test
    public void test_add_a_user() {
        User user1 = constructParameter("2425209089@qq.com", "123456", "唐宝亮");
        User user2 = constructParameter("2425209090@qq.com", "123456", "唐宝亮");
        userMapper.insertSelective(user1);
        userMapper.insertSelective(user2);
        final List<User> users = userMapper.selectByExample(new UserExample());
        assert users.size() ==  2;

        User user3 = users.get(0);
        User user4 = users.get(1);

        assert user1.getEmail().equals(user3.getEmail());
        assert user2.getEmail().equals(user4.getEmail());

    }
}