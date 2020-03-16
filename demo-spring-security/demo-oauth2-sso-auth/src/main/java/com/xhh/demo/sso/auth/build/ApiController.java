package com.xhh.demo.sso.auth.build;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * rest 接口
 *
 * @author tiger
 * @version 1.0.0 createTime: 2017/6/2 下午2:08
 */
@Log4j2
@RestController
public class ApiController {

    @Autowired
    private InMemoryTokenStore inMemoryTokenStore;

    final List<Message> messages = Collections.synchronizedList(build());

    @RequestMapping(path = "api/messages", method = RequestMethod.GET)
    List<Message> getMessages(@RequestParam("token") String token) {
        if (inMemoryTokenStore.readAccessToken(token) == null) {
            log.debug("readAccessToken null");
        }
        if (inMemoryTokenStore.readAccessToken(token).isExpired()) {
            log.debug("expired !");
        }
        return messages;
    }

    @RequestMapping(path = "api/messages", method = RequestMethod.POST)
    Message postMessage(Principal principal, @RequestBody Message message) {
        message.username = principal.getName();
        message.createdAt = LocalDateTime.now();
        messages.add(0, message);
        return message;
    }

    @Getter
    @Setter
    public static class Message {
        public String text;
        public String username;
        public LocalDateTime createdAt;
    }

    public LinkedList<Message> build(){
        Message message = new Message();
        message.setText("12");
        message.setUsername("aaa");
        LinkedList<Message> linkedList = new LinkedList<>();
        linkedList.add(message);
        return linkedList;
    }
}
