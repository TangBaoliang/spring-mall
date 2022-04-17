package ltd.itlover.ltd.springbootmall.listener;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import ltd.itlover.ltd.springbootmall.pojo.PayInfo;
import ltd.itlover.ltd.springbootmall.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author TangBaoLiang
 * @date 2022/4/17
 * @email developert163@163.com
 **/
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayListener {
    @Resource
    private OrderService orderService;



    @RabbitHandler
    public void process(String msg) {
        log.info("【接收到消息】=> {}", msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            //修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }

    }

}
