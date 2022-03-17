package ltd.itlover.ltd.springbootmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ltd.itlover.ltd.springbootmall.enums.ProductStatusEnum;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.mapper.ProductMapper;
import ltd.itlover.ltd.springbootmall.pojo.Product;
import ltd.itlover.ltd.springbootmall.service.CategoryService;
import ltd.itlover.ltd.springbootmall.service.ProductService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.CategoryVo;
import ltd.itlover.ltd.springbootmall.vo.ProductDetailVo;
import ltd.itlover.ltd.springbootmall.vo.ProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductMapper productMapper;

    @Override
    public Result<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId!=null) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet.size() > 0 ? categoryIdSet:null);

        List<ProductVo> productVos = products.stream().map(this::toProductVo).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(productVos);
        return Result.success(pageInfo);
    }

    @Override
    public Result<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null
                || product.getStatus().equals(ProductStatusEnum.DELETED.getCode())
                || product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())) {
            return Result.error(ResultCodeEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        return Result.success(productDetailVo);
    }

    private ProductVo toProductVo(Product product) {
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        return productVo;
    }
}
