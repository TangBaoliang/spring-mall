package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.CategoryVo;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    Result<List<CategoryVo>> selectAll();
    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
