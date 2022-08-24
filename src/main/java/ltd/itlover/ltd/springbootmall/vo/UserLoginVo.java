package ltd.itlover.ltd.springbootmall.vo;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserLoginVo {
    @Email(message = "邮箱格式不合法")
    @NotBlank
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
