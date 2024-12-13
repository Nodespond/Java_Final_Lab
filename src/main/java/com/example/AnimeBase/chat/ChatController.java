package com.example.AnimeBase.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")        // Указывает, что этот метод будет обрабатывать сообщения, отправленные на адрес "/chat.sendMessage".
    @SendTo("/topic/public")        //сообщения будут отправлены на адрес "/topic/public".
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage        //Извлекает полезную нагрузку из сообщения и преобразует её в объект ChatMessage.
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor        // Позволяет доступ к заголовкам и атрибутам сессии сообщения.
    ) {
        //Добавляем пользователя в сессию чата
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());     // Сохраняет имя отправителя в атрибутах сессии, чтобы можно было отслеживать пользователей.
        return chatMessage;     // Возвращает полученное сообщение, которое будет отправлено всем подписчикам на "/topic/public".
    }
}