package org.example.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import org.example.model.AlipayOrder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 17:55:00
 */
public interface AliPayService {

    AlipayResponse pay(AlipayOrder order) throws AlipayApiException;

    /**
     * 异步通知签名验证
     * @param request
     * @return
     */
    boolean notifySignVerified(HttpServletRequest request);

}
