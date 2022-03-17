package ltd.itlover.ltd.springbootmall.controller;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import ltd.itlover.ltd.springbootmall.service.ProductService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;

    @GetMapping("/products")
    @ApiOperation(value="根据类别id查询所有该类以及子类的商品", authorizations = { @Authorization(value="Authorization") })
    public Result<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return productService.list(categoryId, pageNum, pageSize);
    }

    @GetMapping("/detail")
    @ApiOperation(value="根据商品 id 查询商品详细", authorizations = { @Authorization(value="Authorization") })
    public Result detail(@ApiParam("商品id") @RequestParam(required = true) Integer productId) {
        return productService.detail(productId);
    }
}
