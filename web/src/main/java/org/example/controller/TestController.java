package org.example.controller;

import com.alipay.api.response.AlipayTradePagePayResponse;
import org.example.utils.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StringRedisTemplate template;
    @GetMapping("/pay")
    public String pay(){
        template.opsForValue().set("hello","world",300);
        String result = template.opsForValue().get("hello");
        return result;
    }
}
