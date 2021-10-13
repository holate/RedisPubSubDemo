package org.holate.redispubsubdemo.controller;

import com.sun.istack.internal.NotNull;
import org.holate.redispubsubdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author holate
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @RequestMapping("/pub")
    public ResponseEntity<String> pub(@NotNull String channel, @NotNull String message) {
        testService.pub(channel, message);
        return ResponseEntity.ok("success");
    }

    @RequestMapping("/sub")
    public ResponseEntity<String> sub(@NotNull String channel, @NotNull String key) {
        testService.sub(key, channel);
        return ResponseEntity.ok("success");
    }

    @RequestMapping("/unSub")
    public ResponseEntity<String> unSub(@NotNull String channel, @NotNull String key) {
        testService.unSub(key, channel);
        return ResponseEntity.ok("success");
    }
}
