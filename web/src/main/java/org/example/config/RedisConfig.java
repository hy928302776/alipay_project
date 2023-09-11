package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yansf
 * @Description:redis配置
 * @Date: 3:18 PM 2018/4/18
 * @Modified By:
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)

public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.max-redirects}")
    private String redirects;
    @Value("${spring.redis.timeout}")
    private String timeout;
    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        RedisSerializer<Object> serializer = new GenericJackson2JsonRedisSerializer();
        template.setDefaultSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    /**
     * @Author: yansf
     * @Description:redis集群配置
     * @Date: 17:45 2020/4/28
     * @Modified By: 
     */
    @Bean
    public RedisClusterConfiguration getClusterConfiguration() {
        if (host.split(",").length > 1) {
            //如果是host是集群模式的才进行以下操作
            Map<String, Object> source = new HashMap<String, Object>();

            source.put("spring.redis.cluster.nodes", host);

            source.put("spring.redis.cluster.timeout", timeout.replace("ms",""));

            source.put("spring.redis.cluster.max-redirects", redirects);

            source.put("spring.redis.cluster.password", password);

            return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));
        } else {
            return null;
        }

    }
    /**
     * @Author: yansf
     * @Description:集群遍历
     * @Date: 17:50 2020/4/28
     * @Modified By: 
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        if (host.split(",").length == 1) {
            JedisConnectionFactory factory = new JedisConnectionFactory();
            factory.setHostName(host.split(":")[0]);
            factory.setPort(Integer.valueOf(host.split(":")[1]));
            factory.setPassword(password);
            factory.setTimeout(Integer.parseInt(timeout.replace("ms","")));
            return factory;
        } else {
            JedisConnectionFactory jcf = new JedisConnectionFactory(getClusterConfiguration());
            jcf.setPassword(password); //集群的密码认证
            return jcf;
        }
    }

}