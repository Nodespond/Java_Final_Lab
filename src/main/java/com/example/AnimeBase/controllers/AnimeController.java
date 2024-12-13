package com.example.AnimeBase.controllers;

import com.example.AnimeBase.logger.Text_Logger;
import com.example.AnimeBase.logger.Text_Logger;
import com.example.AnimeBase.models.Anime;
import com.example.AnimeBase.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;
    private static final Logger logger = LoggerFactory.getLogger(AnimeController.class);

    @GetMapping("/")
    public String animes(@RequestParam(name = "name", required = false) String name, Model model) {
        // Загружаем список аниме из базы данных
        model.addAttribute("animes", animeService.list(name));
        return "animes"; // Возвращаем имя шаблона
    }

    @GetMapping("/anime/{id}")
    public String animeInfo(@PathVariable Long id, Model model) {
        Anime anime =  animeService.getAnimeById(id);
        model.addAttribute("anime",anime);
        model.addAttribute("images",anime.getImages());
        return "anime-info";
    }
    @Async
    @PostMapping("/anime/create")
    public String createAnime(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,@RequestParam("file3") MultipartFile file3, Anime anime, Model model) throws IOException {
        try {
            try {
                Thread.sleep(5000); // Имитация длительной операции
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            animeService.validateAnime(anime); // Валидация данных
            animeService.saveAnime(anime,file1,file2,file3);
            Text_Logger.loging_data("Аниме успешно добавлено, Название: " + anime.getName());
            logger.info("Аниме добавлено: {}", anime.getName());
            return "redirect:/"; // Перенаправление на главную страницу
        } catch (IllegalTypeException e) {
            model.addAttribute("typeerror",e.getMessage());
            model.addAttribute("animes", animeService.list(null));
            Text_Logger.loging_data("Ошибка при добавлении аниме: " + e.getMessage());
            logger.error("Ошибка при добавлении аниме: {}", e.getMessage());
             // Возвращаемся на страницу с формой добавления аниме
        } catch (IllegalNameException f){
            model.addAttribute("nameerror",f.getMessage());
            model.addAttribute("animes", animeService.list(null));
            Text_Logger.loging_data("Ошибка при добавлении аниме: " + f.getMessage());
            logger.error("Ошибка при добавлении аниме: {}", f.getMessage());
             // Возвращаемся на страницу с формой добавления аниме
        } catch (IllegalSeriesException s) {
            model.addAttribute("serieserror",s.getMessage());
            model.addAttribute("animes", animeService.list(null));
            Text_Logger.loging_data("Ошибка при добавлении аниме: " + s.getMessage());
            logger.error("Ошибка при добавлении аниме: {}", s.getMessage());
        } catch (IllegalFotoException foto){
            model.addAttribute("fotoerror",foto.getMessage());
            model.addAttribute("animes", animeService.list(null));
            Text_Logger.loging_data("Ошибка при добавлении аниме: " + foto.getMessage());
            logger.error("Ошибка при добавлении аниме: {}", foto.getMessage());
        }
        return "animes";
    }

    @PostMapping("/anime/delete/{id}")
    public String deleteAnime(@PathVariable Long id) {
        Text_Logger.loging_data("Аниме добавлено, id аниме =  " + id);
        logger.info("Аниме удалено: {}", id);
        animeService.deleteAnime(id);
        return "redirect:/";
    }
}