'use strict';       //для избежания ошибок и производительности кода(режим)
//получаем элементы страницы
var usernamePage = document.querySelector('#username-page');//имя пользователя
var chatPage = document.querySelector('#chat-page');        //страница чата
var usernameForm = document.querySelector('#usernameForm');     //форма воода имени пользователя
var messageForm = document.querySelector('#messageForm');       //форма отправки сообщения
var messageInput = document.querySelector('#message');      //для ввода текста сооющения
var messageArea = document.querySelector('#messageArea');       //область отображения сообщений
var connectingElement = document.querySelector('.connecting');      //состояние подключения

var stompClient = null;     //для хранения клиента СТОМП
var username = null;        //для хранения имени пользователя

var colors = [      //цвета аватаров пользователей
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];
//подключаемся к веб-сокет клиенту
function connect(event) {
    username = document.querySelector('#name').value.trim();        //получаем имя пользователя и удаляем лишние пробелы

    if(username) {
        usernamePage.classList.add('hidden');       //скрываем форму ввода имени пользователя и показываем страницу чата
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');     //подключаемся к веб-сокету по указанному URL
        stompClient = Stomp.over(socket);       //оборачиваем сокер, создаем клиент СТОМП

        stompClient.connect({}, onConnected, onError);      //подключаемся к СТОМП серверу, передаем пустоту, устанавливаем обработчики для подключений и ошибок
    }
    event.preventDefault();
}

//функция при успешном подключении
function onConnected() {
    // подписываемся на публичный топик
    stompClient.subscribe('/topic/public', onMessageReceived);

    // говорим имя серверу
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');      //скрываем состояние подключения
}

//при ошибке подключения
function onError(error) {
    connectingElement.textContent = 'Невозможно подключится к веб-сокету';
    connectingElement.style.color = 'red';
}

//функция для отправки сообщения
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {     //сообщение не пусто и СТОМП инициализирован
        var chatMessage = {     //объект сообщения чата
            sender: username,       //отправитель
            content: messageInput.value,        //содержимое
            type: 'CHAT'        //тип сообщений
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));     //отправляем сообщение на адрес
        messageInput.value = '';  //очищаем поле ввода смс
    }
    event.preventDefault();
}

//функция для обработки полученных сообщений
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);     //парсим тело сообщения из JSON в объект

    var messageElement = document.createElement('li');      //элемент списка для отображения смс

    if(message.type === 'JOIN') {       //в зависимости от класса сообщения даем ему стилизацию и текст
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');        //элемент для отображения аватара
        var avatarText = document.createTextNode(message.sender[0]);        //первая буква имени отправителя в аватар пихаем
        avatarElement.appendChild(avatarText);      //текст в аватар
        avatarElement.style['background-color'] = getAvatarColor(message.sender);       //цвет фона аватара

        messageElement.appendChild(avatarElement);      //аватар к элементу сообщения добавили

        var usernameElement = document.createElement('span');       // Создает элемент для отображения имени пользователя.
        var usernameText = document.createTextNode(message.sender);     // Получает имя отправителя.
        usernameElement.appendChild(usernameText);      // Добавляет имя пользователя в элемент.
        messageElement.appendChild(usernameElement);        // Добавляет элемент имени пользователя к сообщению.
    }

    var textElement = document.createElement('p');      // Создает элемент для отображения текста сообщения.
    var messageText = document.createTextNode(message.content);     // Получает текст сообщения.
    textElement.appendChild(messageText);       // Добавляет текст сообщения в элемент.

    messageElement.appendChild(textElement);        // Добавляет текст сообщения к элементу сообщения.

    messageArea.appendChild(messageElement);        // Добавляет элемент сообщения в область сообщений.
    messageArea.scrollTop = messageArea.scrollHeight;       // Прокручивает область сообщений вниз, чтобы показать последнее сообщение.
}

// Функция для получения цвета аватара на основе имени пользователя
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);     //путем математики короче на основе символов имени.
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)  // Добавляет обработчик события для формы ввода имени
messageForm.addEventListener('submit', sendMessage, true)       //для отправки смс
