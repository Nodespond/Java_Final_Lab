package com.example.AnimeBase.controllers;

import com.example.AnimeBase.models.Anime;
import com.example.AnimeBase.services.AnimeService;
import com.example.AnimeBase.services.IllegalNameException;
import com.example.AnimeBase.services.IllegalSeriesException;
import com.example.AnimeBase.services.IllegalTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;

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

    @PostMapping("/anime/create")
    public String createAnime(@RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,@RequestParam("file3") MultipartFile file3, Anime anime, Model model) throws IOException {
        try {
            animeService.validateAnime(anime); // Валидация данных
            animeService.saveAnime(anime,file1,file2,file3);
            return "redirect:/"; // Перенаправление на главную страницу
        } catch (IllegalTypeException e) {
            System.out.println(e.getMessage());
            model.addAttribute("typeerror",e.getMessage());
            model.addAttribute("animes", animeService.list(null));
             // Возвращаемся на страницу с формой добавления аниме
        } catch (IllegalNameException f){
            System.out.println(f.getMessage());
            model.addAttribute("nameerror",f.getMessage());
            model.addAttribute("animes", animeService.list(null));
             // Возвращаемся на страницу с формой добавления аниме
        } catch (IllegalSeriesException s) {
            System.out.println(s.getMessage());
            model.addAttribute("serieserror",s.getMessage());
            model.addAttribute("animes", animeService.list(null));
        }
        return "animes";
    }

    @PostMapping("/anime/delete/{id}")
    public String deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return "redirect:/";
    }
}