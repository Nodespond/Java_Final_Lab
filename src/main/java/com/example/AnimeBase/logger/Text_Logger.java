package com.example.AnimeBase.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Text_Logger {
    private static final String LOG_FILE_PATH = "logs/non_automatic_logs.log";

    // Метод для записи логов
    public static void loging_data(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(timestamp + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог: " + e.getMessage());
        }
    }
}