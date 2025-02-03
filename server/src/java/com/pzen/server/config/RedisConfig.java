package com.pzen.server.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${sky.redis.host}")
    private String host;
    @Value("${sky.redis.port}")
    private int port;
    @Value("${sky.redis.password}")
    private String password;
    @Value("${sky.redis.timeout}")
    private int timeout;
    @Value("${sky.redis.pool.maxActive}")
    private int maxActive;
    @Value("${sky.redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${sky.redis.pool.minIdle}")
    private int minIdle;
    @Value("${sky.redis.pool.maxWait}")
    private int maxWait;

    @Value("${sky.redis.database0}")
    private int db0;
    @Value("${sky.redis.database1}")
    private int db1;
    @Value("${sky.redis.database2}")
    private int db2;
    @Value("${sky.redis.database3}")
    private int db3;
    @Value("${sky.redis.database4}")
    private int db4;
    @Value("${sky.redis.database5}")
    private int db5;
    @Value("${sky.redis.database6}")
    private int db6;
    @Value("${sky.redis.database7}")
    private int db7;
    @Value("${sky.redis.database8}")
    private int db8;
    @Value("${sky.redis.database9}")
    private int db9;

    @Bean
    public GenericObjectPoolConfig getPoolConfig() {
        // 配置redis连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        return poolConfig;
    }


    @Bean(name = "redisTemplate0")
    public StringRedisTemplate stringRedisTemplate0() {
        return stringRedisTemplate(db0);
    }

    @Bean(name = "redisTemplate1")
    public StringRedisTemplate stringRedisTemplate1() {
        return stringRedisTemplate(db1);
    }

    @Bean(name = "redisTemplate2")
    public StringRedisTemplate stringRedisTemplate2() {
        return stringRedisTemplate(db2);
    }

    @Bean(name = "redisTemplate3")
    public StringRedisTemplate stringRedisTemplate3() {
        return stringRedisTemplate(db3);
    }

    @Bean(name = "redisTemplate4")
    public StringRedisTemplate stringRedisTemplate4() {
        return stringRedisTemplate(db4);
    }

    @Bean(name = "redisTemplate5")
    public StringRedisTemplate stringRedisTemplate5() {
        return stringRedisTemplate(db5);
    }

    @Bean(name = "redisTemplate6")
    public StringRedisTemplate stringRedisTemplate6() {
        return stringRedisTemplate(db6);
    }

    @Bean(name = "redisTemplate7")
    public StringRedisTemplate stringRedisTemplate7() {
        return stringRedisTemplate(db7);
    }

    @Bean(name = "redisTemplate8")
    public StringRedisTemplate stringRedisTemplate8() {
        return stringRedisTemplate(db8);
    }

    @Bean(name = "redisTemplate9")
    public StringRedisTemplate stringRedisTemplate9() {
        return stringRedisTemplate(db9);
    }

    @Bean(name = "objectRedisTemplate0")
    public RedisTemplate<String, Object> objectRedisTemplate0() {
        return objectRedisTemplate(db0);
    }

    @Bean(name = "objectRedisTemplate1")
    public RedisTemplate<String, Object> objectRedisTemplate1() {
        return objectRedisTemplate(db1);
    }

    @Bean(name = "objectRedisTemplate2")
    public RedisTemplate<String, Object> objectRedisTemplate2() {
        return objectRedisTemplate(db2);
    }

    @Bean(name = "objectRedisTemplate3")
    public RedisTemplate<String, Object> objectRedisTemplate3() {
        return objectRedisTemplate(db3);
    }

    @Bean(name = "objectRedisTemplate4")
    public RedisTemplate<String, Object> objectRedisTemplate4() {
        return objectRedisTemplate(db4);
    }

    @Bean(name = "objectRedisTemplate5")
    public RedisTemplate<String, Object> objectRedisTemplate5() {
        return objectRedisTemplate(db5);
    }

    @Bean(name = "objectRedisTemplate6")
    public RedisTemplate<String, Object> objectRedisTemplate6() {
        return objectRedisTemplate(db6);
    }

    @Bean(name = "objectRedisTemplate7")
    public RedisTemplate<String, Object> objectRedisTemplate7() {
        return objectRedisTemplate(db7);
    }

    @Bean(name = "objectRedisTemplate8")
    public RedisTemplate<String, Object> objectRedisTemplate8() {
        return objectRedisTemplate(db8);
    }

    @Bean(name = "objectRedisTemplate9")
    public RedisTemplate<String, Object> objectRedisTemplate9() {
        return objectRedisTemplate(db9);
    }

    private StringRedisTemplate stringRedisTemplate(int database) {
        // 构建工厂对象
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        if (password != null && !password.isEmpty()) {
            config.setPassword(password);
        }
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(timeout))
                .poolConfig(getPoolConfig())
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        // 设置使用的redis数据库
        factory.setDatabase(database);
        // 重新初始化工厂
        factory.afterPropertiesSet();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.disableDefaultTyping(); // 禁用默认类型信息
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(factory);
        // 使用构造函数注入 ObjectMapper
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(om, Object.class);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        stringRedisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        stringRedisTemplate.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        stringRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        stringRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    private RedisTemplate<String, Object> objectRedisTemplate(int database) {
        // 构建工厂对象
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        if (password != null && !password.isEmpty()) {
            config.setPassword(password);
        }
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(timeout))
                .poolConfig(getPoolConfig())
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        // 设置使用的redis数据库
        factory.setDatabase(database);
        // 重新初始化工厂
        factory.afterPropertiesSet();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.disableDefaultTyping(); // 禁用默认类型信息

        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        // 序列化配置
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(om, Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}

