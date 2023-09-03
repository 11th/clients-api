# Clients-API

## Описание
Spring Boot приложение, обеспечивающее хранение информации о клиентах и их контактой информации. Каждый клиент характеризуется именем. Каждому клиенту в соответствие может быть поставлена информация о его контактах: 0 и более телефонных номеров, 0 и более адресов электронной почты.

## API
API обеспечивает следующие функции:

- Добавление нового клиента\
`POST http://<host>:<port>/api/clients`
- Добавление нового контакта клиента (телефон или email)\
`POST http://<host>:<port>/api/clients/<client id>/contacts`
- Получение списка клиентов\
`GET http://<host>:<port>/api/clients?offset=0&limit=10`
- Получение информации по заданному клиенту (по id)\
`GET http://<host>:<port>/api/clients/<client id>`
- Получение списка контактов заданного клиента\
`GET http://<host>:<port>/api/clients/<client id>/contacts`
- Получение списка контактов заданного типа заданного клиента\
`GET http://<host>:<port>/api/clients/<client id>/contacts?type=PHONE/EMAIL`

OpenAPI документация доступна по ссылке [http://host:port/api-docs](http://host:port/api-docs)

## Установка и запуск
- Собрать jar файл с помощью команды `mvn clean install`
- Запустить приложение из папки target с помощью команды `java -jar clients-api-0.0.1-SNAPSHOT.jar`
> Дополнительно нужно указать параметры базы данных:
> - spring.datasource.url
> - spring.datasource.username
> - spring.datasource.password

## Стек
- Java 17
- Spring Boot 3.1.3
- PostgreSQL
