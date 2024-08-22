# StreamMessenger

StreamMessenger — это простой сервис для отправки текстовых сообщений (email) с возможностью расширения для отправки SMS и сообщений в мессенджерах.

## Требования

- Java 17
- Maven 3.x
- PostgreSQL
- Mailtrap (для тестирования email-сообщений)

## Установка

1. Клонируйте репозиторий:

    ```bash
    git clone https://github.com/aleksashk/stream-messenger.git
    cd stream-messenger
    ```

2. Сконфигурируйте базу данных PostgreSQL и создайте необходимые таблицы. Сервис использует Liquibase для управления миграциями базы данных. Liquibase автоматически выполнит миграции при запуске приложения.

3. Настройте параметры почтового сервиса с использованием Mailtrap:

    Откройте `EmailSenderService.java` и замените следующие параметры на ваши:

    ```java
    props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Используйте хост из Mailtrap
    props.put("mail.smtp.port", "2525"); // Используйте порт из Mailtrap
    props.put("mail.smtp.auth", "true");

    return new PasswordAuthentication("your_mailtrap_username", "your_mailtrap_password");
    ```

4. Соберите и запустите приложение:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Использование

Сервис предоставляет REST API для работы с сообщениями.

### Отправка email-сообщения

Отправка email-сообщения осуществляется с помощью следующего HTTP POST запроса:

POST http://localhost:8080/api/email/send


#### Параметры запроса:
- `to`: email получателя (например, `test@example.com`)
- `subject`: тема письма (например, `Test Subject`)
- `content`: текст сообщения (например, `This is a test email.`)

### Пример запроса

```bash
curl -X POST "http://localhost:8080/api/email/send" \
  -d "to=test@example.com" \
  -d "subject=Test Subject" \
  -d "content=This is a test email."

Структура проекта
model/ — содержит сущности базы данных.
repositories/ — содержит JPA репозитории для доступа к данным.
services/ — содержит сервисы для бизнес-логики.
controllers/ — содержит REST API контроллеры.
resources/db/changelog/ — содержит файлы Liquibase для управления миграциями базы данных.
Расширение функциональности
Архитектура сервиса позволяет легко добавлять новые типы сообщений (SMS, мессенджеры). Для этого нужно создать новый сервис, реализующий базовый интерфейс для отправки сообщений, и добавить его в текущую реализацию, используя паттерн декоратор.

Лицензия
Этот проект лицензируется под лицензией MIT. Подробности см. в файле LICENSE.
