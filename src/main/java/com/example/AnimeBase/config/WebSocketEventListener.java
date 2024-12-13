package com.example.AnimeBase.config;
import com.example.AnimeBase.chat.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;   //для работы с заголовками STOMP-сообщений.
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;     //для обработки событий отключения сессий WebSocket.

@Component      //класс является компонентом Spring и будет управляться контейнером Spring.
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;       // для отправки сообщений через WebSocket.

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {       // Метод для обработки события отключения сессии WebSocket.
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());      // Оборачиваем сообщение в StompHeaderAccessor для доступа к заголовкам.
        String username = (String) headerAccessor.getSessionAttributes().get("username");       // Извлекаем имя пользователя из атрибутов сессии.
        if (username != null) {
            var chatMessage = ChatMessage.builder()     // Создаем новое сообщение чата с помощью паттерна Builder.
                    .type(MessageType.LEAVE)        // Устанавливаем тип сообщения как "LEAVE" (пользователь покинул чат).
                    .sender(username)       //отправитель
                    .build();       //формируем объект ChatMessage
            messagingTemplate.convertAndSend("/topic/public", chatMessage);     //отправляем сообщение по адресу
        }
    }
}
