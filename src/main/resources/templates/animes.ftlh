<!DOCTYPE html>
<html>
<head>
    <title>ANIME</title>
</head>
<body>
    <h1>ANIME</h1>
    <h3>Tovati is Japan</h3>

    <form action="/" method="get">
        Поиск по названию аниме: <input type="text" name="name"><br>
        <input type="submit" value="Поиск"/>
    </form>

    <#list animes as anime>
        <div>
            <p>
                <b>${anime.name}</b>  ${anime.series} |
                <a href="/anime/${anime.id}"> Подробнее...</a>

            </p>

        </div>
        <#else>
        <h3>Аниме нет</h3>
    </#list>
    <hr>
    <h3>Создать новое аниме</h3>

    <form action="/anime/create" method="post" enctype="multipart/form-data">
        Название аниме: <input type="text" name="name"/>
        <#if nameerror??>
            <div style="color: red;">${nameerror}</div> <!-- Отображаем сообщение об ошибке -->
        </#if>
        <br><br>
        Описание аниме: <input type="text" name="description"/>
        <br><br>
        Серии аниме: <input type="number" name="series" value="0"/>
        <#if serieserror??>
             <div style="color: red;">${serieserror}</div> <!-- Отображаем сообщение об ошибке -->
        </#if>
        <br><br>
        Тип аниме: <input type="text" name="type"/>
        <#if typeerror??>
            <div style="color: red;">${typeerror}</div> <!-- Отображаем сообщение об ошибке -->
        </#if>
        <br><br>
        Фото 1: <input type="file" name="file1"/><br><br>
        Фото 2: <input type="file" name="file2"/><br><br>
        Фото 3: <input type="file" name="file3"/><br><br>
        <input type="submit" value="Добавить товар"/><br><br>
    </form>

    <a href="/index.html">
            <button>Перейти в чат</button>
        </a>
</body>

</html>