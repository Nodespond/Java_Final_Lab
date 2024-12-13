package com.example.AnimeBase.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor; // аннотация для автоматической генерации конструктора с параметрами для всех полей.
import lombok.Data;     // аннотация для автоматической генерации методов доступа (геттеров и сеттеров), equals, hashCode и toString.
import lombok.NoArgsConstructor;        //для генерации конструкторов без параметров

@Entity     // этот класс является сущностью JPA и будет отображаться в таблице базы данных.
@Table(name = "images")     //указал имя таблицы в БД с которой связана модель будет
@Data       //генерирует методы
@AllArgsConstructor     //конструкторы с параметрами и без
@NoArgsConstructor
public class Image {
    @Id     //это поле есть ID модели
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //значение ID генерируется БД
    @Column(name = "id")        //имена столбцов в БД для этих полей
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "originalFileName")
    private String originalFileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "contentType")
    private String contentType;
    @Column(name = "isPreviewImage")
    private boolean isPreviewImage;         //превью фото
    @Lob            //данное поле в бд храним в типе LOB, то есть большой объект
    private byte[] bytes;       //байтовое представление изображения


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)      //много фотографий к одному аниме
    private Anime anime;        //храним ссылку на Аниме , для которого изображение
}
