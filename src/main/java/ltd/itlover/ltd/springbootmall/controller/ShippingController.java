package ltd.itlover.ltd.springbootmall.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ltd.itlover.ltd.springbootmall.constance.MallConstance;
import ltd.itlover.ltd.springbootmall.enums.ResultCodeEnum;
import ltd.itlover.ltd.springbootmall.pojo.Shipping;
import ltd.itlover.ltd.springbootmall.pojo.User;
import ltd.itlover.ltd.springbootmall.service.ShippingService;
import ltd.itlover.ltd.springbootmall.utils.Result;
import ltd.itlover.ltd.springbootmall.vo.AddShippingVo;
import ltd.itlover.ltd.springbootmall.vo.DelShippingVo;
import ltd.itlover.ltd.springbootmall.vo.UpdateShippingVo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ShippingController {

    @Resource
    private ShippingService shippingService;

    @GetMapping("/shippings")
    @ApiOperation(value="根据返回用户的收获地址列表", authorizations = { @Authorization(value="Authorization") })
    public Result shippingList (@ApiIgnore HttpSession httpSession,
                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        User user = (User) httpSession.getAttribute(MallConstance.CURRENT_USER);
        if (user == null) {
            return Result.error(ResultCodeEnum.NEED_LOGIN);
        }
        Integer userId = user.getId();
        return shippingService.getByUserId(userId, pageNum, pageSize);
    }


    @ApiOperation(value="批量删除收获地址", authorizations = { @Authorization(value="Authorization") })
    @DeleteMapping("/shippings/{shippingId}")
    public Result batchDelShipping (@PathVariable Integer shippingId, @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstance.CURRENT_USER);
        if (user == null) {
            return Result.error(ResultCodeEnum.NEED_LOGIN);
        }
        Integer userId = user.getId();
        List<Integer> ids = new ArrayList<>();
        ids.add(shippingId);
        return shippingService.batchDelShipping(ids, userId);
    }


    @ApiOperation(value="增加收获地址", authorizations = { @Authorization(value="Authorization") })
    @PostMapping("/shippings")
    public Result add (@RequestBody AddShippingVo addShippingVo, @ApiIgnore HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConstance.CURRENT_USER);
        if (user == null) {
            return Result.error(ResultCodeEnum.NEED_LOGIN);
        }
        if (addShippingVo.getReceiverPhone() == null && addShippingVo.getReceiverMobile() == null) {
            return Result.error(ResultCodeEnum.CONTACT_WAY_NULL);
        }
        Integer userId = user.getId();
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(addShippingVo, shipping);
        shipping.setUserId(userId);
        return shippingService.add(shipping);
    }


    @ApiOperation(value="修改收获地址", authorizations = { @Authorization(value="Authorization") })
    @PutMapping("/shippings/{shippingId}")
    public Result modify (@PathVariable Integer shippingId, @RequestBody UpdateShippingVo updateShippingVo, @ApiIgnore HttpSession httpSession) {
        Integer userId = ((User) httpSession.getAttribute(MallConstance.CURRENT_USER)).getId();
        if (updateShippingVo.getReceiverPhone() == null && updateShippingVo.getReceiverMobile() == null) {
            return Result.error(ResultCodeEnum.CONTACT_WAY_NULL);
        }
        Shipping shipping = new Shipping();
        shipping.setUserId(userId);
        shipping.setId(shippingId);
        BeanUtils.copyProperties(updateShippingVo, shipping);
        return shippingService.modify(shipping);
    }
}
