package ltd.itlover.ltd.springbootmall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ltd.itlover.ltd.springbootmall.service.CategoryService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.CategoryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    @ApiOperation(value="查询所有商品类别", authorizations = { @Authorization(value="Authorization") })
    public Result<List<CategoryVo>> getAll () {
        return categoryService.selectAll();
    }
}
