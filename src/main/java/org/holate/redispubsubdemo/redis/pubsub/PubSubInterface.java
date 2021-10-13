package org.holate.redispubsubdemo.redis.pubsub;

import org.springframework.data.redis.connection.MessageListener;

/**
 * 订阅取消订阅
 *
 * @author holate
 */
public interface PubSubInterface {

    /**
     * 订阅一个 channel.
     *
     * @param id       作为唯一标识
     * @param channel  频道
     * @param listener 收到消息后的处理器
     */
    void sub(String id, String channel, MessageListener listener);

    /**
     * 对 channel 取消订阅.
     *
     * @param id      唯一标识
     * @param channel 频道
     */
    void unSub(String id, String channel);

    /**
     * 对 channel 发送消息
     *
     * @param channel 频道
     * @param message 消息
     */
    void pub(String channel, String message);
}
