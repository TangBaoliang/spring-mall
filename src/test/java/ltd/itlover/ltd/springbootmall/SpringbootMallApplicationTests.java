package ltd.itlover.ltd.springbootmall;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource("classpath:application.yaml")
@MapperScan("ltd.itlover.ltd.springbootmall.mapper")

class SpringbootMallApplicationTests {

    @Test
    void contextLoads() {
    }

}
