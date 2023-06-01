package org.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 17:52:33
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayProperties {
    /**
     * 支付宝网关
     */
    private String gatewayUrl;
    /**
     * 支付宝应用的appid
     */
    private String appId;
    /**
     * 应用私钥
     */
    private String appPrivateKey;
    /**
     * 报文格式
     */
    private String format;
    /**
     * 字符串编码格式
     */
    private String charset;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;
    /**
     * 支付完成后同步跳转的路径（一般写支付完成后想要跳转的路径）
     */
    private String returnUrl;
    /**
     * 异步通知url
     */
    private String notifyUrl;

}
