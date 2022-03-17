package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.mapper.UserMapper;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.pojo.UserExample;
import ltd.itlover.ltd.springbootmall.service.UserService;
import ltd.itlover.ltd.springbootmall.utils.Result;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Result register(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail());
        if (userMapper.countByExample(userExample) != 0L) {
            throw new RuntimeException("该Email已经被注册");
        }

        //MD5 摘要
        String digestedPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(digestedPassword);

        //写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            throw new RuntimeException("注册失败");
        }

        return Result.success();
    }

    @Override
    public Result login(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail());
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList == null || userList.size() <= 0) {
            throw new RuntimeException("该邮箱没有注册账户");
        }
        User persisUser = userList.get(0);
        if (!persisUser.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)))) {
            throw new RuntimeException("密码错误");
        }
        persisUser.setPassword(null);
        return Result.success(persisUser);
    }
}
