package com.example.AnimeBase.repositories;

import com.example.AnimeBase.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;  // интерфейс JpaRepository из Spring Data JPA,  стандартные методы для работы с БД.

public interface ImageRepository extends JpaRepository<Image, Long> {// Определение интерфейса ImageRepository, его расширяет JpaRepository.
    //Этот репозиторий дает методы для создания, чтения , удаления объекта Image, Long - Id объекта просто
}
