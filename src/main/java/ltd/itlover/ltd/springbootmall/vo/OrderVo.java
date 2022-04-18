package ltd.itlover.ltd.springbootmall.vo;


import lombok.Data;
import ltd.itlover.ltd.springbootmall.pojo.Shipping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 廖师兄
 */
@Data
public class OrderVo {

	private Long orderNo;

	private BigDecimal payment;

	private Integer paymentType;

	private Integer postage;

	private Integer status;

	private Date paymentTime;

	private Date sendTime;

	private Date endTime;

	private Date closeTime;

	private Date createTime;

	private List<OrderItemVo> orderItemVoList;

	private Integer shippingId;

	private String shipping;
}
