package ltd.itlover.ltd.springbootmall.service.impl;

import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.mapper.ShippingMapper;
import ltd.itlover.ltd.springbootmall.pojo.Shipping;
import ltd.itlover.ltd.springbootmall.pojo.ShippingExample;
import ltd.itlover.ltd.springbootmall.service.ShippingService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    @Override
    public Result getByUserId(Integer userId) {

        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria().andUserIdEqualTo(userId);
        List<Shipping> shippingList = shippingMapper.selectByExample(shippingExample);

        return Result.success(shippingList);
    }

    @Override
    public Result batchDelShipping(List<Integer> ids, Integer userId) {
        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria().andUserIdEqualTo(userId).andIdIn(ids);
        int count = shippingMapper.deleteByExample(shippingExample);

        if (count <= 0) {
            return Result.error(ResultCodeEnum.DELETE_ERROR);
        }

        return Result.success(ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result add(Shipping shipping) {
        shipping.setCreateTime(new Date());
        shipping.setUpdateTime(new Date());
        int count = shippingMapper.insertSelective(shipping);
        if (count <= 0) {
            return Result.error(ResultCodeEnum.ADD_ERROR);
        }
        return Result.success(ResultCodeEnum.SUCCESS);
    }

    @Override
    public Result modify(Shipping shipping) {
        shipping.setUpdateTime(new Date());
        ShippingExample shippingExample = new ShippingExample();
        shippingExample.createCriteria().andUserIdEqualTo(shipping.getUserId()).andIdEqualTo(shipping.getId());
        int count = shippingMapper.updateByExample(shipping, shippingExample);

        if (count <= 0) {
            return Result.error(ResultCodeEnum.MODIFY_ERROR);
        }
        return Result.success(ResultCodeEnum.SUCCESS);
    }


}
