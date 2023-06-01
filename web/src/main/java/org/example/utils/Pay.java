package org.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;


/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/5/24 14:43:49
 */
public class Pay {
    private final static String URL="https://openapi.alipaydev.com/gateway.do";//支付宝网关（固定）。
    private final static String APPID="2021000122692484";//APPID 即创建应用后生成。
    private final static String PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCTaIjFx2QUlEIZ3EB47ctdY2xok4zf7mszL2uE38B/egQqX9bnsZygoTqGMLpKl0ZoqUrPuqJqUjPBq7mxBS85Cmz5ZsDletUWDXhdU+hmmRbcv2l0iG1oIfIqIGqLWYGiJmMzkqNad/mBnAmUVXdhzmiZbBF9eI7ppSMNKOKFHYxOyc+J7EsddF3Q8DRVOxk8z52fnPCBwcrzArikpMRC8+WIilCzMx44uIxjO+/AyyAwiNTLL5FF5LkIQkqDnbZtGfH2Ha3yXGP/LZfsZCGvWChMIyHFascKsZ3g1enFRqj8Rye5cQyzqn3/LAewkmO+7I3T3O9TdxjIX+dQ2R7NAgMBAAECggEBAI1K65636mQXX+ytw1amiAGx5R2wIJ4cR8C+OU8Zscf+yrNbPgDDrCADqrcpScdpjOGRM9Y9TKSgul9vHQRitcr1BVNhfI/51vnWW4MJhH+jSdi52HkMs+HWd+qNO9LqLmmgZgHvEokK6qujR0NZUJ79XCGDBpi/0CyAT0Kz57wzC0b+MA00ucUO1GIXikPtACh5pJj8pF7c0eybJOUpcB7NHmxz3TxK2OeTh4BDQv7Wi0FA2vApTqB79KT79ylTgxEv4gnsJ9raNMmapXmNTeTe1PKzoTevWTXtC8gcz8L75KhJm4iE2v4k7jIyQBGM3WnsLO9ojrUDrDiJmle4zUECgYEAzp7LMN0qIgjvR1vawhX1eG2yshVy9A8Lx2hzYSuk2xNZ90zxOfFrZv+rS1CWyblmCZ/Aa+RiI8RcgcN/BDhwIgNoEhX11j7y7vmajDpjBXNcgMNKJWGnEhd2AaP7unN+AW3IlyeGrNTpcxRhEAmXw7OUL8b4gRQEo4F7PvesnikCgYEAtqMaR0mOH55HFsNpImxMas5GNuWdtcCd93CnN3TEprvTfBHwgS2DdVL3/C1gMJ/JhtkcNnZCSW6+JLKnZjCH/fFuDIh83JZIHWFA/Wov1NW/etSvmQuyGRlBCBbFRWZsH32tw+KcwkzNhyKCD1BmeBQ9NNGKzpSoyuv9tXDayAUCgYAZX4a0SXSTGWXI7Y6/ptKuJ0p6dAfZ0L8xjbIdjK6B+K5joB8/blYyH9N5PDxIKWxOYj/PAAzNN5/kj41Zh1S92I0/K1M1LCXJEv3XmpPuZ2xEo7bHQGaz4g2rxedzzhr7MfAgH8p6DutcJnSE86ifb74S3+xEbaDkM1JfOSsJgQKBgHQ22PPfpCYOayHa+j1DOGUGGLu55F7Q0DOxZVXuYWvPPMVIDQfF1E6FmvLGVNzb8Dp0UHV/TrK3Wxqy674kx4QZanNjLkCXCBBZ4CbguMrvsEYSrCHwxUUfQrhI9QIyKD7pk/Z/bkxc3rNMjAH+oRVrm37Oj0rgYX+uJuSbiTuZAoGBAK6fMQqNA+qE2I21hT2GMzJGXss+c+1EDHoYoSHrF3ALJ/F40hCogcpEhBnk3ldxzNaynOtjrqQkh9D94TmQhe58pMl+cXHJl/XNK9z5lDea0J684LIBdkCpkk/u5WTSNOp1bHzycr7Nc0sCKfkyLAIkRnXNl3Iw6YAmW1r1T/tX";//开发者私钥，由开发者自己生成。
    private final static String CHARSET="UTF-8";//编码集，支持 GBK/UTF-8。
    private final static String SIGN_TYPE="RSA2";//生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA，推荐使用 RSA2。
    private final static String APP_CERT_PATH= "/Users/huangying/IdeaProjects/alipay_project/web/src/main/resources/appPublicCert.crt";//应用公钥证书文件本地路径。
    private final static String alipay_cert_path= "/Users/huangying/IdeaProjects/alipay_project/web/src/main/resources/alipayPublicCert.crt";//支付宝公钥证书文件本地路径。
    private final static String alipay_root_cert_path= "/Users/huangying/IdeaProjects/alipay_project/web/src/main/resources/alipayRootCert.crt";//支付宝根证书文件本地路径。

    public static AlipayClient config() throws AlipayApiException {
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(URL);
        certAlipayRequest.setAppId(APPID);
        certAlipayRequest.setPrivateKey(PRIVATE_KEY);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        certAlipayRequest.setCertPath(APP_CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(alipay_cert_path);
        certAlipayRequest.setRootCertPath(alipay_root_cert_path );
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        return alipayClient;
    }


    public static AlipayTradePagePayResponse pay(){

        try {
            AlipayClient alipayClient = config();
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            //异步接收地址，仅支持http/https，公网可访问
            request.setNotifyUrl("http://www.baidu.com");
            //同步跳转地址，仅支持http/https
            request.setReturnUrl("http://www.baidu.com");
            /******必传参数******/
            JSONObject bizContent = new JSONObject();
            //商户订单号，商家自定义，保持唯一性
            bizContent.put("out_trade_no", "20210817010101004");
            //支付金额，最小值0.01元
            bizContent.put("total_amount", 0.01);
            //订单标题，不可使用特殊符号
            bizContent.put("subject", "测试商品");
            //电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
            bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
            request.setBizContent(bizContent.toString());
            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
            return response;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        pay();
    }
}
