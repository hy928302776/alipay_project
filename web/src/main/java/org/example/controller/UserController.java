package org.example.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayTradePagePayResponse;
import org.example.utils.Pay;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 17:15:42
 */
@RestController
public class UserController {
    @RequestMapping("/test")
    public String getNameById(String id){
        AlipayTradePagePayResponse result =Pay.pay();
        if(Objects.nonNull(result) && result.isSuccess()){
            System.out.println("调用成功");
            return result.getBody();
        }
        System.out.println("调用失败");
        return "error";

    }
}
