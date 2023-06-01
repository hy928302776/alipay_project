package org.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.config.AliPayProperties;
import org.example.model.AlipayOrder;
import org.example.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 17:55:45
 */
@Slf4j
@Service
public class AliPayServiceImpl implements AliPayService {
    @Autowired
    private AliPayProperties aliPayProperties;
    @Override
    public AlipayResponse pay(AlipayOrder order) throws AlipayApiException {

        //初始化AlipayConfig
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl(aliPayProperties.getGatewayUrl());
        alipayConfig.setAppId(aliPayProperties.getAppId());
        alipayConfig.setSignType(aliPayProperties.getSignType());
        alipayConfig.setAlipayPublicKey(aliPayProperties.getAlipayPublicKey());
        alipayConfig.setPrivateKey(aliPayProperties.getAppPrivateKey());
        alipayConfig.setFormat(aliPayProperties.getFormat());
        alipayConfig.setCharset(aliPayProperties.getCharset());
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

        //设置请求参数
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称
        AlipayTradePagePayRequest  request = new AlipayTradePagePayRequest();
        //设置同步返回url
        request.setReturnUrl(aliPayProperties.getReturnUrl());
        //设置异步通知url
        request.setNotifyUrl(aliPayProperties.getNotifyUrl());
        log.debug("JSON.toJSONString(order):" + JSON.toJSONString(order));
        //转换为json字符串后传入
        request.setBizContent(JSON.toJSONString(order));

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

        return response;
    }

    @Override
    public boolean notifySignVerified(HttpServletRequest request) {
        log.debug("进入了异步通知");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();

        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        System.out.println(params.toString());
        System.out.println(aliPayProperties.toString());
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, aliPayProperties.getAlipayPublicKey(), aliPayProperties.getCharset(), aliPayProperties.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println("SDK验证签名结果1：" + signVerified);

        return signVerified;

    }
}
