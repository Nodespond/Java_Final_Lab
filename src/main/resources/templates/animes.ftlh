<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ANIME БИБЛИОТЕКА</title>
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

        form {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px 0;
        }

        input[type="text"],
        input[type="number"],
        input[type="file"],
        input[type="submit"],
        button {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"],
        button {
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
        }

        input[type="submit"]:hover,
        button:hover {
            background-color: #0056b3;
        }

        .error {
            color: red;
            font-size: 0.9em;
        }

        .anime-item {
            background-color: #fff;
            border-radius: 8px;
            padding: 10px;
            margin: 10px 0;
            box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
        }

        .anime-item a {
            color: #007BFF;
            text-decoration: none;
        }

        .anime-item a:hover {
            text-decoration: underline;
        }

        hr {
            border: 1px solid #ccc;
        }

        .chat-button {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h1>БИБЛИОТЕКА АНИМЕ</h1>
<h3>Здесь собраны лучшие тайтлы!</h3>

<form action="/" method="get">
    Поиск по названию аниме:
    <input type="text" name="name" placeholder="Введите название аниме">
    <input type="submit" value="Поиск"/>
</form>

<#list animes as anime>
<div class="anime-item">
    <p>
        <b>${anime.name}</b> Число серий:  ${anime.series}
        <img src="/images/${anime.previewImageId}"
             class="card-img-top"><br><br>
        <a href="/anime/${anime.id}"> Подробнее...</a>
    </p>
</div>
<#else>
<h3>Аниме нет</h3>
</#list>

<hr>
    <h3>Добавить новое аниме</h3>

    <form action="/anime/create" method="post" enctype="multipart/form-data">
        Название аниме:
        <input type="text" name="name" placeholder="Введите название аниме"/>
        <#if nameerror??>
        <div class="error">${nameerror}</div> <!-- Отображаем сообщение об ошибке -->
    </#if>

            Описание аниме:
    <input type="text" name="description" placeholder="Введите описание аниме"/>

    Серии аниме:
    <input type="number" name="series" value="0" placeholder="Введите количество серий"/>
    <#if serieserror??>
    <div class="error">${serieserror}</div> <!-- Отображаем сообщение об ошибке -->
</#if>

        Тип аниме:
<input type="text" name="type" placeholder="Введите тип аниме"/>
<#if typeerror??>
<div class="error">${typeerror}</div> <!-- Отображаем сообщение об ошибке -->
</#if>


        <#if fotoerror??>
            <div class="error">${fotoerror}</div> <!-- Отображаем сообщение об ошибке -->
        </#if>
        Основное фото: <input type="file" name="file1"/><br>
Фото 2: <input type="file" name="file2"/><br>
    Фото 3: <input type="file" name="file3"/><br>

    <input type="submit" value="Добавить аниме"/>
</form>

    <div class="chat-button">
        <a href="/index.html">
            <button>Перейти в чат</button>
        </a>
    </div>
</body>
</html>