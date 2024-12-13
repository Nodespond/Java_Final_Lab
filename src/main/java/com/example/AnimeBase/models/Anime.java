package com.example.AnimeBase.models;

import jakarta.persistence.*;  //все аннотаций и классы из пакета Jakarta Persistence (JPA) для работы с БД.
import lombok.AllArgsConstructor;
import lombok.Data;     //автоматической генерации методов доступа
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;         //для работы с датой и временем
import java.util.ArrayList;     //динамические массивы
import java.util.List;      //списки

@Entity     //класс будет отображаться в таблице БД
@Table(name="animes")       //имя таблицы в БД с которым связываем
@Data       //методы доступа и прочее
@AllArgsConstructor
@NoArgsConstructor
public class Anime {
    @Id     //это поле будет ID
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
    private String imagePath;       //путь к картинке аниме

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    mappedBy = "anime")     //связь один ко многим, подгружаем лениво
    private List<Image> images = new ArrayList<>();         //список картинок с данным аниме
    private Long previewImageId;        //для получения превью фото
    private LocalDateTime dateOfCreated;        //дата создания аниме

    @PrePersist     //метод инициализации, метод вызывается перед сохранением объекта в БД
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToAnime(Image image){
        image.setAnime(this);
        images.add(image);
    }

    public Long getPreviewImageId(){
        return previewImageId;
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
