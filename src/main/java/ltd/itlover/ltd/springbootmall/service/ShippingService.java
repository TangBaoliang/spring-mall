package ltd.itlover.ltd.springbootmall.service;

import ltd.itlover.ltd.springbootmall.pojo.Shipping;
import ltd.itlover.ltd.springbootmall.utils.Result;

import java.util.List;

public interface ShippingService {
    Result getByUserId (Integer userId, Integer pageNum, Integer pageSize);

    Result batchDelShipping(List<Integer> ids, Integer userId);

    Result add(Shipping shipping);

    Result modify(Shipping shipping);
}
