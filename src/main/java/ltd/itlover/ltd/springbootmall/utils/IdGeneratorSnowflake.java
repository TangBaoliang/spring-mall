package ltd.itlover.ltd.springbootmall.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author TangBaoLiang
 * @date 2022/4/23
 * @email developert163@163.com
 **/
@Slf4j
@Component
public class IdGeneratorSnowflake {

    /**
     * 机房id
     */
    @Value("${snowflake.workerId}")
    private long workerId;

    /**
     * 机器id
     */
    @Value("${snowflake.datacenterId}")
    private long datacenterId;
    private Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);

    //构造后开始执行，加载初始化工作
    @PostConstruct
    public void init(){
        try{
            //获取本机的ip地址编码
            //workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
            log.info("当前机器的workerId: " + workerId);
            log.info("当前机房id：" + datacenterId);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("当前机器的workerId获取失败 ----> " + e);
            workerId = NetUtil.getLocalhostStr().hashCode();
        }
    }

    public synchronized long snowflakeId(){
        return snowflake.nextId();
    }

    public synchronized long snowflakeId(long workerId, long datacenterId){
        Snowflake snowflake = IdUtil.createSnowflake(workerId, datacenterId);
        return snowflake.nextId();
    }

}