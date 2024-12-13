package com.example.AnimeBase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;   //для настройки брокера сообщений.
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;       //поддержку WebSocket с использованием брокера сообщений.
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;      //для регистрации конечных точек STOMP.
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//Брокер сообщений — это серверное приложение, которое управляет обменом сообщениями между клиентами и предоставляет механизмы для отправки, получения и маршрутизации сообщений.
//STOMP — это простой протокол обмена сообщениями, который используется для взаимодействия между клиентами и брокерами сообщений. Он предоставляет текстовый формат для обмена сообщениями
//Когда вы используете WebSocket и STOMP в своем приложении, брокер сообщений обрабатывает сообщения, отправляемые клиентами через WebSocket. Клиенты могут подключаться к брокеру, отправлять и получать сообщения, подписываться на темы и обрабатывать полученные данные.
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {  // Метод для настройки брокера сообщений.
        registry.setApplicationDestinationPrefixes("/app");  //префикс для адресов назначения, к которым будут отправляться сообщения от клиентов
        registry.enableSimpleBroker("/topic");      // Включает простой брокер сообщений, который будет обрабатывать сообщения, отправляемые на адреса
    }

    @Override       //переопределение метода интерфейса
    public void registerStompEndpoints(StompEndpointRegistry registry) {        // Метод для регистрации конечных точек STOMP.
        registry.addEndpoint("/ws").withSockJS();
    }
}
