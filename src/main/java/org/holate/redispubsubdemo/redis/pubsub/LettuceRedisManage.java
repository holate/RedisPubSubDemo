package org.holate.redispubsubdemo.redis.pubsub;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.Subscription;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * \.
 *
 * @author holate
 */
@Component
public class LettuceRedisManage implements PubSubInterface {

    private final LettuceConnectionFactory lettuceConnectionFactory;
    private final RedisConnection publishConnection;

    private final Map<String, RedisConnection> redisConnectionMap = new ConcurrentHashMap<>();

    @Autowired
    public LettuceRedisManage(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
        this.publishConnection = lettuceConnectionFactory.getConnection();
    }

    @Override
    public void sub(String id, String channel, MessageListener listener) {
        RedisConnection redisConnection = lettuceConnectionFactory.getConnection();
        redisConnectionMap.put(id + "_" + channel, redisConnection);
        redisConnection.subscribe(listener, channel.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void unSub(String id, String channel) {
        RedisConnection redisConnection = redisConnectionMap.get(id + "_" + channel);
        if (redisConnection != null) {
            Subscription subscription = redisConnection.getSubscription();
            if (subscription != null) {
                subscription.unsubscribe(channel.getBytes(StandardCharsets.UTF_8));
                subscription.pUnsubscribe(channel.getBytes(StandardCharsets.UTF_8));
            }
            redisConnectionMap.remove(id + "_" + channel);
        }
    }

    @Override
    public void pub(String channel, String message) {
        publishConnection.publish(channel.getBytes(StandardCharsets.UTF_8),
            message.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 另一种 pub方式
     */
    public void pub1(String channel, String message) {
        RedisClient client = getRedisClient();
        StatefulRedisPubSubConnection<String, String> pubSubConnection = client.connectPubSub();
        RedisPubSubCommands<String, String> pubSubCommands = pubSubConnection.sync();
        pubSubCommands.publish(channel, message);
    }

    private RedisClient getRedisClient() {
        RedisURI redisUri = RedisURI.builder()
            .withHost("127.0.0.1")
            .withPort(6379)
            .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
            .build();
        return RedisClient.create(redisUri);
    }
}
