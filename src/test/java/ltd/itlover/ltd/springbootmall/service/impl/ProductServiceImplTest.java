package ltd.itlover.ltd.springbootmall.service.impl;

import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.springbootmall.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application.yaml")
@MapperScan("ltd.itlover.ltd.springbootmall.mapper")
@Slf4j
class ProductServiceImplTest {
    @Resource
    private ProductService productService;

    @Test
    void list() {
        productService.list(null, 1, 10).getData();
    }
}