package com.nplit.controller;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import com.nplit.service.ChatService;
import com.nplit.vo.ChatMessageVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    @Inject // byType으로 자동 주입
	ChatService service;

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageVO message){
        message.setMessage(message.getSendId() + "님이 쉐어링에 참가하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
       // service.insertSendMsg(message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageVO message){
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        service.insertSendMsg(message);
    }
}