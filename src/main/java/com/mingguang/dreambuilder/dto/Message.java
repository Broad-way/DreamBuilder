package com.mingguang.dreambuilder.dto;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    String messageId;
    MessageData messageData;
    String type;
    String fromPeerId;
    String toPeerId;
    @Data
    class MessageData{
        @Data
        class sdp{
            String type;
            String sdp;
        }
        @Data
        class candidate{
            String candidate;
            String sdpMid;
            String sdpMLineIndex;
            String usernameFragment;
        }
        candidate candidate;
        String peerId;
        sdp sdp;
        List<String> peerList;
    }
}

