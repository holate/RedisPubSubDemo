package org.holate.redispubsubdemo.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

/**
 * Lettuce配置.
 *
 * @author holate
 */
@Configuration
public class LettuceRedisConfig {

    /**
     * @param redisStandaloneConfiguration {@link RedisConfig#redisStandaloneConfiguration()}
     * @param lettucePoolConfig            {@link #lettucePoolConfig}
     * @return
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
                                                             LettucePoolingClientConfiguration lettucePoolConfig) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
    }

    /**
     * @param options {@link #clientOptions()}
     * @param dcr
     * @return
     */
    @Bean
    LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources dcr) {
        return LettucePoolingClientConfiguration.builder()
            .poolConfig(new GenericObjectPoolConfig())
            .clientOptions(options)
            .clientResources(dcr)
            .build();
    }

    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
            .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
            .autoReconnect(true)
            .build();
    }
}
