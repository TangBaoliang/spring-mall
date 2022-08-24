package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.mapper.CategoryMapper;
import ltd.itlover.ltd.springbootmall.pojo.Category;
import ltd.itlover.ltd.springbootmall.service.CategoryService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;


    /**
     * 先从数据库中查询出所有的类别，然后在处理好类别的归属关系返回给前端，因为深度不会太大，所以使用了递归
     * @return 返回类别的结构和关系
     */
    @Override
    public Result<List<CategoryVo>> selectAll() {
        List<CategoryVo> categoryVoList = new LinkedList<>();

        //查询出所有的类别
        List<Category> categories = categoryMapper.selectAll();
        //查出 parentId = 0的数据
        categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(MallConstance.ROOT_PARENT_ID))
                .map(this::toCategoryVo)
                .collect(Collectors.toList());

        //查询子目录
        categoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());;
         findSubCategory(categoryVoList, categories);
        return Result.success(categoryVoList);
    }

    /**
     * 分类下的所以子分类id以及当前分类 id,一般用于商品查询
     * @param id 分类id
     * @param resultSet 结果
     */
    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id, categories, resultSet);
        resultSet.add(id);
    }

    private void findSubCategoryId(Integer id, List<Category> categories, Set<Integer> resultSet) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(), categories, resultSet);
            }
        }
    }


    /**
     * 获取商品分类列表和结构
     * @param categoryVoList 需要填补子项列表的父列表
     * @param categories 所有分类，从中选取需要父类的子项
     *                  这里使用了递归的方法，知道所有子项没有子项的时候就到了递归的终点
     */
    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                //如果查到内容，设置 subcategory，继续往下查
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = toCategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }

                //使用了递归
                //先排个序
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                categoryVo.setSubCategories(subCategoryVoList);
                findSubCategory(subCategoryVoList, categories);
            }

        }
    }

    private CategoryVo toCategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
