package org.holate.redispubsubdemo.redis.pubsub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateManage implements PubSubInterface {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;

    private final Map<String, MessageListener> listenerMap = new ConcurrentHashMap<>();

    @Override
    public void sub(String id, String channel, MessageListener listener) {
        ChannelTopic channelTopic = new ChannelTopic(channel);
        //订阅pattern
        redisMessageListenerContainer.addMessageListener(listener, channelTopic);
        listenerMap.put(id + "_" + channel, listener);
    }

    @Override
    public void unSub(String id, String channel) {
        MessageListener messageListener = listenerMap.get(id + "_" + channel);
        if (messageListener != null) {
            redisMessageListenerContainer.removeMessageListener(messageListener);
        }
    }

    @Override
    public void pub(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }

}
