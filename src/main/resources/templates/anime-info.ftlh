<!DOCTYPE html>
<html>
<head>
    <title>ANIME</title>
</head>
<body>
    <h1>ANIME</h1>
    <h3>Подробная инфа о аниме</h3>
    <#list images as img>
        <img src="/images/${img.id}" height="60px" /><br><br>
    </#list>
    <b> Название аниме: </b>${anime.name}<br>
    <b> Описание товара: </b>${anime.description}<br>
    <b> Число серий : </b>${anime.series}<br>
    <b> Типа аниме: </b>${anime.type}<br>
    <hr>
    <h3>Создать новое аниме</h3>
    <form action="/anime/delete/${anime.id}" method="post">
        <input type="submit" value="Удалить"/>
    </form>
</body>

</html>