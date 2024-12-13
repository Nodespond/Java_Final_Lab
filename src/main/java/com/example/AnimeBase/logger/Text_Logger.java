package com.example.AnimeBase.logger;

import java.io.BufferedWriter;      //для записи текстовых данных в файл.
import java.io.FileWriter;      // позволяет записывать данные в файл.
import java.io.IOException;     //обработка ошибок ввода-вывода
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;      //для форматирования даты и времени.

public class Text_Logger {
    private static final String LOG_FILE_PATH = "logs/non_automatic_logs.log";      //путь к логам ручным

    // Метод для записи логов
    public static void loging_data(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {     //открытие буфера для записи
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));      //получаем текущее время и дату
            writer.write(timestamp + " - " + message);      //записываем лог
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог: " + e.getMessage());
        }
    }
}