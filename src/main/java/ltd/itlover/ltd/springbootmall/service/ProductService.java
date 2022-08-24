package ltd.itlover.ltd.springbootmall.service;

import com.github.pagehelper.PageInfo;
import ltd.itlover.ltd.springbootmall.pojo.Product;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.ProductDetailVo;
import ltd.itlover.ltd.springbootmall.vo.ProductVo;

import java.util.List;

public interface ProductService {
    Result<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);
    Result<ProductDetailVo> detail(Integer productId);
}
