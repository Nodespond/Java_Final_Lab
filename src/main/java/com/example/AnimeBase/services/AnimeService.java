package com.example.AnimeBase.services;

import com.example.AnimeBase.models.Anime;
import com.example.AnimeBase.models.Image;
import com.example.AnimeBase.repositories.AnimeRepository;      //импортируем интерфейс для работы с БД
import lombok.RequiredArgsConstructor;          //импорт аннотации для автомат.создания конструкторов
import lombok.extern.slf4j.Slf4j;       //аннотация для логирования "автоматического"
import org.springframework.scheduling.annotation.Async;     //для асинхронных методом (включение многопоточности)
import org.springframework.stereotype.Service;      //импорт для обозначения класса , который является сервисом в Spring
import org.springframework.web.multipart.MultipartFile;     //чтобы работать с загружаемыми файлами(картинками)

import java.io.IOException;     //исключение для обработки ошибок ввода-вывода
import java.util.List;          //интерфейс для работа с коллекциями
import java.util.regex.Pattern;     //для работы с регулярными выражениями

@Service        //класс является сервисом
@Slf4j      //логирование добавили
@RequiredArgsConstructor        //генерируем конструкторы для полей класса (final)
public class AnimeService {

    private final AnimeRepository animeRepository;      //обьявляем чтобы работать с базой данных АНИМЕ

    // Регулярные выражения для проверки форматов
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9А-Яа-я ]+$"); // Пример: только буквы и цифры
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^[A-Za-z0-9А-Яа-я .,!?]+$"); // Пример: текст с разрешенными символами
    private static final Pattern TYPE_PATTERN = Pattern.compile("^(Кодомо|Сёдзё|Дзёсэй|Сёнэн|Сэйнэн)$"); // Пример: только буквы и цифры
    //получаем аниме по его имени из БД
    public List<Anime> list(String name) {
        if (name != null && !name.isEmpty()) {
            return animeRepository.findByName(name); // Если имя указано, ищем по имени
        }
        return animeRepository.findAll(); // Если имя не указано, возвращаем все аниме
    }

    //Метод для сохранения аниме
    @Async      //выполняем асинхронно (я буду замедлять искусственно поток)
    public void saveAnime(Anime anime, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException, IllegalFotoException {
        Image image1;
        Image image2;
        Image image3;

        try {
            Thread.sleep(1000); // Имитация задержки
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();     //восстанавливаем прерывание потока
        }

        if(file1.getSize() != 0){
            image1 = toImageEntity(file1);  //преобразуем из MultipartFile в Image-модель
            image1.setPreviewImage(true);       //устанавливаем его как основное
            anime.addImageToAnime(image1);      //добавляем это изображение к аниме
        }

        if (file1.getSize()==0){
            throw new IllegalFotoException("Ты не добавил основное фото!");     //если не загрузил фотку основную - то исключение
        }

        if(file2.getSize() != 0){
            image2 = toImageEntity(file2);
            anime.addImageToAnime(image2);
        }

        if(file3.getSize() != 0){
            image3 = toImageEntity(file3);
            anime.addImageToAnime(image3);
        }
        //Сохраняем в БД и устанавливаем ID для основного фото
        Anime animeFromDb = animeRepository.save(anime);
        animeFromDb.setPreviewImageId(animeFromDb.getImages().get(0).getId());

        animeRepository.save(anime);
    }

    //Метод для преобразования MultipartFile в Image
    private Image toImageEntity(MultipartFile file) throws IOException{
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    //Метод удаления аниме по ID
    public void deleteAnime(Long id) {
        try {
            Thread.sleep(2000); // Имитация длительной операции
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        animeRepository.deleteById(id);
    }
    //Получаем по ID
    public Anime getAnimeById(Long id) {
        return animeRepository.findById(id).orElse(null);
    }

    // Метод для проверки данных и вывод исключений
    public void validateAnime(Anime anime) throws IllegalArgumentException, IllegalSeriesException {

        if (!isValidName(anime.getName()) || anime.getName()==null) {
            throw new IllegalNameException("Неправильное название аниме - Используй цифры и буквы!");
        }

        if (!isValidType(anime.getType()) || anime.getType()==null) {
            throw new IllegalTypeException("Неправильно указан тип аниме (Допустимы типы: Кодомо,Сёдзё,Дзёсэй,Сёнэн,Сэйнэн)");
        }

        if (anime.getSeries() < 0 ) {
            throw new IllegalSeriesException("Число серий должно быть не меньше нуля");
        }

    }

    //Метод для проверки вводимых нами на соответствие маскам регулярок
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