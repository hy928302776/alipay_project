package org.example.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.model.AlipayOrder;
import org.example.service.AliPayService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 17:59:03
 */
@RestController
@RequestMapping("aliPay")
@Slf4j
public class AliPayController {
    @Autowired
    private AliPayService aliPayService;

    @GetMapping("/pay")
    public String pay(HttpServletRequest request) throws AlipayApiException {
        AlipayOrder order = new AlipayOrder();
        order.setSubject("测试商品");
        order.setOut_trade_no("2021081701010100411");
        order.setTotal_amount("100.00");

        AlipayResponse response = aliPayService.pay(order);
        if (response.isSuccess()){
            System.out.println("创建订单成功");
            //创建订单
        }
        System.out.println("response.getBody():"+response.getBody());
        return response.getBody();
    }


    @PostMapping("/notify")
    public R<Void> getNotifyMsg(HttpServletRequest request) throws UnsupportedEncodingException {

        boolean signVerified = aliPayService.notifySignVerified(request);

        if (signVerified) {
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            if (trade_status.equals("TRADE_FINISHED")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序

                log.debug("成功:TRADE_FINISHED");
                // 注意：
                // 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                return R.success();
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                // 判断该笔订单是否在商户网站中已经做过处理
                // 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                // 如果有做过处理，不执行商户的业务程序
                log.debug("成功:TRADE_SUCCESS");

                // 注意：
                // 付款完成后，支付宝系统发送该交易状态通知
                return R.success();
            } else {//验证失败

                // 调试用，写文本函数记录程序运行情况是否正常
                return R.fail("异常");
            }

        }

        return R.success();
    }
}

