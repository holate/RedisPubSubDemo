package org.holate.redispubsubdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    String host;
    @Value("${spring.redis.port}")
    int port;
    @Value("${spring.redis.password:}")
    String password;
    @Value("${spring.redis.timeout}")
    int timeout;
    @Value("${spring.redis.jedis.pool.max-active}")
    int maxActive;
    @Value("${spring.redis.jedis.pool.max-wait}")
    long maxWaitMillis;
    @Value("${spring.redis.jedis.pool.max-idle}")
    int maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    int minIdle;

    /**
     * redis 基础配置.
     *
     * @return redis基础配置
     */
    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        return redisStandaloneConfiguration;
    }
}
