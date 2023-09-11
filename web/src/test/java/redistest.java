import redis.clients.jedis.Jedis;

/**
 * @author huangying
 * @version 1.0
 * @project alipay_project
 * @description
 * @date 2023/9/11 16:24:47
 */
public class redistest {
    public static void main(String[] args) {
        // 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。

        Jedis jedis = new Jedis("10.1.120.42", 6379);
        jedis.set("hello","world");
        String result = jedis.get("hello");

        System.out.println(result);

        jedis.close();
    }
}
