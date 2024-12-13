<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ANIME</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        h3 {
            color: #666;
            text-align: center;
        }

        .image-container {
            display: flex;
            justify-content: center; /* Центрирование изображений */
            margin-bottom: 20px;
            flex-wrap: wrap; /* Позволяет изображениям переноситься на новую строку, если не помещаются */
        }

        .image-container img {
            width: 25%; /* Ширина 25% от ширины контейнера */
            height: auto; /* Автоматическая высота для сохранения пропорций */
            max-height: 50vh; /* Максимальная высота 30% от высоты вьюпорта */
            margin: 0 10px; /* Отступы между изображениями */
            border-radius: 5px; /* Закругление углов изображений */
            box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2); /* Тень для изображений */
        }

        .info {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px 0;
        }

        .info b {
            color: #333;
        }

        hr {
            border: 1px solid #ccc;
        }

        .delete-button {
            text-align: center;
            margin-top: 20px;
        }

        input[type="submit"] {
            background-color: #dc3545; /* Красный цвет для кнопки удаления */
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #c82333; /* Темнее при наведении */
        }
    </style>
    <script>
        function confirmDelete() {
            return confirm("Вы уверены, что хотите удалить это аниме?");
        }
    </script>
</head>
<body>
<h1>ANIME</h1>
<h3>Подробная инфа о аниме</h3>

<div class="image-container">
    <#list images as img>
    <img src="/images/${img.id}" alt="Изображение аниме"/>
</#list>
</div>

<div class="info">
    <b>Название аниме:</b> ${anime.name}<br>
    <b>Описание товара:</b> ${anime.description}<br>
    <b>Число серий:</b> ${anime.series}<br>
    <b>Тип аниме:</b> ${anime.type}<br>
</div>

<hr>

    <div class="delete-button">
        <form action="/anime/delete/${anime.id}" method="post" onsubmit="return confirmDelete();">
            <input type="submit" value="Удалить"/>
        </form>
    </div>

    <div class="delete-button">
        <a href="/">
        <button>Вернуться на главную страницу</button>
        </a>
    </div>
</body>
</html>