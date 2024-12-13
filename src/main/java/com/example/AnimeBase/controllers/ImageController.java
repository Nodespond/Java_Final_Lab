package com.example.AnimeBase.controllers;

import com.example.AnimeBase.models.Image;
import com.example.AnimeBase.repositories.ImageRepository;          //интерфейс для работы с данными изображений.
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;     // класс для работы с потоками ввода-вывода.
import org.springframework.http.MediaType;      //для работы с типами медиа.
import org.springframework.http.ResponseEntity;     //для создания ответов HTTP.
import org.springframework.web.bind.annotation.GetMapping;      //аннотация для обработки GET-запросов.
import org.springframework.web.bind.annotation.PathVariable;        //для извлечения переменных из URL.
import org.springframework.web.bind.annotation.RestController;      //класс является контроллером REST.

import java.io.ByteArrayInputStream;        //для работы с массивами байтов как с входными потоками., для обработки картинки

@RequiredArgsConstructor        //конструктор с параметрами для всех final полей класса.
@RestController     //класс возвращает данные в формате JSON или XML.
public class ImageController {
    private final ImageRepository imageRepository;      //зависимость от ImageRepository для работы с данными изображений.

    @GetMapping("/images/{id}")     //обрабатываем GET-запросы по URL "/images/{id}".
    private ResponseEntity<?> getImageById(@PathVariable Long id){      //Метод для получения изображения по его ID.
        Image image = imageRepository.findById(id).orElse(null);
        return ResponseEntity.ok()      // Формируем ответ HTTP с картинкой
                .header("fileName",image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())     //длина содержимого на основе размера изображения.
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));     //преобразуем байты изображения в поток.
    }
}
