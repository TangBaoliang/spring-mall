package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.utils.Result;

public interface UserService {
    public Result register(User user);
    public Result login(User user);
}
