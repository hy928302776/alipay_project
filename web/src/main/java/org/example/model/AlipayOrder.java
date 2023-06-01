package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 17:54:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AlipayOrder {

    /**
     * 用户订单号，必填
     */
    private String out_trade_no;

    /**
     * 订单名称，必填
     */
    private String subject;

    /**
     * 订单总金额，必填
     */
    private String total_amount;

    /**
     * 销售产品码，必填，注：目前电脑支付场景下仅支持FAST_INSTANT_TRADE_PAY，所以可以写死
     */
    private final String product_code = "FAST_INSTANT_TRADE_PAY";

}