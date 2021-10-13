package org.holate.redispubsubdemo.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;

/**
 * \.
 */
@Slf4j
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        byte[] bytesBody = message.getBody();
        byte[] bytesChannel = message.getChannel();
        log.info("channel={}, pattern={}, msgBody={}",
            new String(bytesChannel), pattern, new String(bytesBody));
    }
}
