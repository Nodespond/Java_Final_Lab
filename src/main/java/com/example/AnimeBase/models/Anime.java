package com.example.AnimeBase.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="animes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "series")
    private int series;
    @Column(name = "type")
    private String type;
    @Column(name = "image")
    private String imagePath;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "anime")
    private List<Image> images = new ArrayList<>();
    private Long previewImageId;        //для получения превью фото
    private LocalDateTime dateOfCreated;

    @PrePersist     //метод инициализации
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToAnime(Image image){
        image.setAnime(this);
        images.add(image);
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage(){
        return imagePath;
    }

    public void setImage(String imagePath){
        this.imagePath = imagePath;
    }
}
