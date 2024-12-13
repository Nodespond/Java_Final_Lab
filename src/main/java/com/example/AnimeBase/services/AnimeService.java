package com.example.AnimeBase.services;

import com.example.AnimeBase.models.Anime;
import com.example.AnimeBase.models.Image;
import com.example.AnimeBase.repositories.AnimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    // Регулярные выражения для проверки форматов
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9А-Яа-я ]+$"); // Пример: только буквы и цифры
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^[A-Za-z0-9А-Яа-я .,!?]+$"); // Пример: текст с разрешенными символами
    private static final Pattern TYPE_PATTERN = Pattern.compile("^(Кодомо|Сёдзё|Дзёсэй|Сёнэн|Сэйнэн)$"); // Пример: только буквы и цифры
    //
    public List<Anime> list(String name) {
        if (name != null && !name.isEmpty()) {
            return animeRepository.findByName(name); // Если имя указано, ищем по имени
        }
        return animeRepository.findAll(); // Если имя не указано, возвращаем все аниме
    }

    //Метод для сохранения аниме
    @Async
    public void saveAnime(Anime anime, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException, IllegalFotoException {
        Image image1;
        Image image2;
        Image image3;

        try {
            Thread.sleep(1000); // Имитация длительной операции
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if(file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            anime.addImageToAnime(image1);
        }

        if (file1.getSize()==0){
            throw new IllegalFotoException("Ты не добавил основное фото!");
        }

        if(file2.getSize() != 0){
            image2 = toImageEntity(file2);
            anime.addImageToAnime(image2);
        }

        if(file3.getSize() != 0){
            image3 = toImageEntity(file3);
            anime.addImageToAnime(image3);
        }

        Anime animeFromDb = animeRepository.save(anime);
        animeFromDb.setPreviewImageId(animeFromDb.getImages().get(0).getId());

        animeRepository.save(anime);
    }

    private Image toImageEntity(MultipartFile file) throws IOException{
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteAnime(Long id) {
        try {
            Thread.sleep(2000); // Имитация длительной операции
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        animeRepository.deleteById(id);
    }

    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id).orElse(null);
    }

    // Метод для проверки данных
    public void validateAnime(Anime anime) throws IllegalArgumentException, IllegalSeriesException {

        if (!isValidName(anime.getName()) || anime.getName()==null) {
            throw new IllegalNameException("Неправильное название аниме");
        }

        if (!isValidType(anime.getType()) || anime.getType()==null) {
            throw new IllegalTypeException("Неправильно указан тип аниме");
        }

        if (anime.getSeries() < 0 ) {
            throw new IllegalSeriesException("Число серий должно быть не меньше нуля");
        }

    }

    //Метод для проверки вводимых
    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    private boolean isValidDescription(String description) {
        return DESCRIPTION_PATTERN.matcher(description).matches();
    }

    private boolean isValidType(String type) {
        return TYPE_PATTERN.matcher(type).matches();
    }

}