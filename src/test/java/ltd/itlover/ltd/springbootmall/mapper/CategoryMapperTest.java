package ltd.itlover.ltd.springbootmall.mapper;

import ltd.itlover.ltd.springbootmall.pojo.Category;
import ltd.itlover.ltd.springbootmall.pojo.CategoryExample;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author(作者) 唐宝亮
 * @date(日期) 2022/3/11
 **/
@SpringBootTest
@PropertySource("classpath:application.yaml")
@MapperScan("ltd.itlover.ltd.springbootmall.mapper")
public class CategoryMapperTest {
    @Resource
    private CategoryMapper categoryMapper;
    @Test
    void countByExample() {
        CategoryExample categoryExample = new CategoryExample();
        System.out.println(categoryMapper.selectByExample(categoryExample));
    }

    @Test
    void deleteByExample() {
    }

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
    }

    @Test
    void selectByExample() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByExampleSelective() {
    }

    @Test
    void updateByExample() {
    }

    @Test
    void updateByPrimaryKeySelective() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}