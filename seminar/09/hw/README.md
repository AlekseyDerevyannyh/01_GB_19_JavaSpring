# Курс "Фреймворк Spring" 
## Домашняя работа 9
* **
### Автор:
Деревянных Алексей
* **
### Примечание:

Скриншот страницы **/eureka/apps/**

![screenshot](./screenshot.png "screenshot")

Скриншот страницы **/api/issue/** сервиса **issue-service**

![screenshot1](./screenshot1.png "screenshot1")

* **
### Условие задачи:
1. Восстановить пример, рассмотренный на уроке (запустить эврику и 2 сервиса; заставить их взаимодействовать).<br>
   Сдать скриншот страницы /eureka/apps с зарегистрированными приложениями.
   На скрине должно быть видно оба сервиса (book-service, issuer-service)
2. \* Добавить третий сервис: сервис читателей.<br>
   Обогатить ручку GET /issue, чтобы она возвращала подробную информацию:
    ```json
   [
      {
        "id": "733a8a9f-7fbf-4eb6-9900-f3338007d848",
        "issuedAt": "2024-11-28",
        "book": {
          "id": "78a0d4d5-67db-45f8-b846-da410f01aa11",
          "name": "Absalom, Absalom!",
          "author": {
            "id": "4deeeb5b-f263-4c5f-9c8c-62b83b0977ee",
            "firstName": "Justen",
            "lastName": "Huels"
          }
        },
        "reader": {
          "id": "78a0d4d5-67db-45f8-b846-da410f01bc34",
          "firstName": "Имя читателя",
          "lastName": "Фамилия читателя"
        }
      }
   ]
    ```