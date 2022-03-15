package ltd.itlover.ltd.springbootmall.mapper;

import java.util.List;
import ltd.itlover.ltd.springbootmall.pojo.PayInfo;
import ltd.itlover.ltd.springbootmall.pojo.PayInfoExample;
import org.apache.ibatis.annotations.Param;

public interface PayInfoMapper {
    long countByExample(PayInfoExample example);

    int deleteByExample(PayInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    List<PayInfo> selectByExample(PayInfoExample example);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayInfo record, @Param("example") PayInfoExample example);

    int updateByExample(@Param("record") PayInfo record, @Param("example") PayInfoExample example);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}