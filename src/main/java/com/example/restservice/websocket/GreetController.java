package com.example.restservice.websocket;

import com.example.restservice.RestServiceApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetController {

    private static final Logger log = LoggerFactory.getLogger(GreetController.class);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        //log.debug(message.getName());
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }
}
