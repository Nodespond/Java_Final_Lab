package com.example.AnimeBase.repositories;

import com.example.AnimeBase.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
