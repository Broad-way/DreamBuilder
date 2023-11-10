package com.mingguang.dreambuilder.service;

import com.alibaba.fastjson2.JSON;
import com.mingguang.dreambuilder.dto.Message;
import org.springframework.web.socket.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VideoSocketHandler implements WebSocketHandler {
    private Map<String,WebSocketSession> webSocketSessions=new HashMap();
    @Override
    public void afterConnectionEstablished(
            WebSocketSession session) throws Exception {
        String uuid= UUID.randomUUID().toString();
        webSocketSessions.put(uuid,session);
        //分配id
        session.sendMessage(new TextMessage(JSON.toJSONString(uuid)));
    }

    @Override
    public void handleMessage(WebSocketSession session,
                              WebSocketMessage<?> message) throws Exception {
        message.getPayload();
        System.out.println(message);
        Message msgO= JSON.parseObject((String) message.getPayload(),Message.class);
        if (msgO.getMessageId().equals("PROXY")){
            WebSocketSession toConn = webSocketSessions.get(
                    msgO.getToPeerId());
            if (toConn!=null)
                toConn.sendMessage(new TextMessage(JSON.toJSONString(msgO)));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


}
