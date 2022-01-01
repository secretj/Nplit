package com.nplit.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker // stomp ����� ���� ������̼�
@Configuration
public class StompWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// endpoint�� /stomp�� �ϰ�, allowedOrigins�� "*"�� �ϸ� ����������
	// Get /info 404 Error�� �߻��Ѵ�. �׷��� �Ʒ��� ���� 2���� �������� �и��ϰ�
	// origins�� ���� ���������� �����ϴ� �� �����Ͽ���.
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat").setAllowedOrigins("http://localhost:8080").withSockJS();
	}

	/* ���ø����̼� ���ο��� ����� path�� ������ �� ���� */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/pub"); /*
															 * Client���� SEND ��û�� ó�� Spring docs������ /topic,
															 * /queue�� ������ ���ǻ� /pub, /sub�� ����
															 */

		registry.enableSimpleBroker("/sub"); /*
												 * �ش� ��η� SimpleBroker�� ���. SimpleBroker�� �ش��ϴ� ��θ� SUBSCRIBE�ϴ�
												 * Client���� �޼����� �����ϴ� ������ �۾��� ����
												 */
	}
}