package org.holate.redispubsubdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.holate.redispubsubdemo.redis.listener.MyMessageListener;
import org.holate.redispubsubdemo.redis.pubsub.PubSubInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {
    @Autowired
    private PubSubInterface redisTemplateManage;

    public void pub(String channel, String message) {
        redisTemplateManage.pub(channel, message);
    }

    public void sub(String key, String channel) {
        redisTemplateManage.sub(key, channel, new MyMessageListener());
    }

    public void unSub(String key, String channel) {
        redisTemplateManage.unSub(key, channel);
    }
}
