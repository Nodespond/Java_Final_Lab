package com.example.AnimeBase.repositories;

import com.example.AnimeBase.models.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;      //для работы со списком

public interface AnimeRepository extends JpaRepository<Anime,Long> {        //аналогия с репозиторием Image
    List<Anime> findByName(String name);        //Метод для поиска аниме по имени, Spring Data JPA автоматически реализует этот метод на основе имени.
}
