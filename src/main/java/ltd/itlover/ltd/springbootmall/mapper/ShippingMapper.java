package ltd.itlover.ltd.springbootmall.mapper;

import java.util.List;
import ltd.itlover.ltd.springbootmall.pojo.Shipping;
import ltd.itlover.ltd.springbootmall.pojo.ShippingExample;
import org.apache.ibatis.annotations.Param;

public interface ShippingMapper {
    long countByExample(ShippingExample example);

    int deleteByExample(ShippingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    List<Shipping> selectByExample(ShippingExample example);

    Shipping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Shipping record, @Param("example") ShippingExample example);

    int updateByExample(@Param("record") Shipping record, @Param("example") ShippingExample example);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}