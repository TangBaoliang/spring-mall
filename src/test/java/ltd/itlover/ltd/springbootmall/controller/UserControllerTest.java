package ltd.itlover.ltd.springbootmall.controller;

import ltd.itlover.ltd.springbootmall.mapper.UserMapper;
import ltd.itlover.ltd.springbootmall.pojo.UserExample;
import ltd.itlover.ltd.springbootmall.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author TangBaoLiang
 * @date 2022/5/29
 * @email developert163@163.com
 **/

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource("classpath:application.yaml")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 可以成功注册的输入参数
     * @throws Exception
     */
    @Test
    @Order(1)
    void register_should_add_success() throws Exception {
        String requestBody = "{\n" +
                "  \"email\": \"123123123@qq.com\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"phone\": \"15616905189\",\n" +
                "  \"username\": \"唐宝亮\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "  \"status\": 0,\n" +
                        "  \"msg\": \"成功\",\n" +
                        "  \"data\": null\n" +
                        "}"));
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo("123123123@qq.com");
        assert userMapper.selectByExample(userExample).size() == 1;
    }


    /**
     * 测试已经存在的用户，在该方法里面插入两次就可
     * @throws Exception
     */
    @Test
    @Order(2)
    @DisplayName("测试注册已经存在的用户")
    void register_should_existed() throws Exception {
        String requestBody = "{\n" +
                "  \"email\": \"123123123@qq.com\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"phone\": \"15616905189\",\n" +
                "  \"username\": \"唐宝亮\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "  \"status\": -1,\n" +
                        "  \"msg\": \"该Email已经被注册\",\n" +
                        "  \"data\": null\n" +
                        "}"));
    }

    /**
     * 注册的信息不完整，没有邮箱号
     * @throws Exception
     */
    @Test
    @Order(3)
    @DisplayName("请求参数缺少必要信息")
    void register_should_illegal_param_without_email() throws Exception {
        String requestBody = "{\n" +
                "  \"password\": \"123456\",\n" +
                "  \"phone\": \"15616905189\",\n" +
                "  \"username\": \"唐宝亮\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "  \"status\": -1,\n" +
                        "  \"msg\": \"Value for email cannot be null\",\n" +
                        "  \"data\": null\n" +
                        "}"));
    }

    /**
     * 注册的信息不合法，没有邮箱格式不正确
     * @throws Exception
     */
    @Test
    @Order(4)
    @DisplayName("邮箱格式不正确")
    void register_email_format_illegal() throws Exception {
        String requestBody = "{\n" +
                "  \"email\": \"123123\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"phone\": \"15616905189\",\n" +
                "  \"username\": \"唐宝亮\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "  \"status\": 3,\n" +
                        "  \"msg\": \"邮箱格式不合法\",\n" +
                        "  \"data\": null\n" +
                        "}"));
    }

    /**
     * 没有请求体，提示没有请求体
     * @throws Exception
     */
    @Test
    @Order(5)
    @DisplayName("没有请求体")
    void register_without_info() throws Exception {
        String requestBody = "";
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
                .andExpect(MockMvcResultMatchers.content().json("{\n" +
                        "  \"status\": -1,\n" +
                        "  \"msg\": \"Required request body is missing: public ltd.itlover.ltd.springbootmall.utils.Result ltd.itlover.ltd.springbootmall.controller.UserController.register(ltd.itlover.ltd.springbootmall.pojo.User,org.springframework.validation.BindingResult)\",\n" +
                        "  \"data\": null\n" +
                        "}"));
    }



}