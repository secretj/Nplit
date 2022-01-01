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

	private final SimpMessagingTemplate template; // Ư�� Broker�� �޼����� ����
	@Inject // byType���� �ڵ� ����
	ChatService service;

	// Client�� SEND�� �� �ִ� ���
	// stompConfig���� ������ applicationDestinationPrefixes�� @MessageMapping ��ΰ�
	// ���յ�
	// "/pub/chat/enter"
	@MessageMapping(value = "/chat/enter")
	public void enter(ChatMessageVO message) {
		message.setMessage(message.getSendId() + "���� ����� �����ϼ̽��ϴ�.");
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

	}

	@MessageMapping(value = "/chat/message")
	public void message(ChatMessageVO message) {
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
		service.insertSendMsg(message);
	}
}