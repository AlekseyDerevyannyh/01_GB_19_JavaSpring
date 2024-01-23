# Курс "Фреймворк Spring" 
## Домашняя работа 4
* **
### Автор:
Деревянных Алексей
* **
### Примечание:

Исходники в папке **/src/main/java/ru/gb/**

Запуск приложения осуществляется из файла **Application.java**

### Условие задачи:
```java
  /**
 * 1. В предыдущий проект (где была библиотека с книгами, выдачами и читателями) добавить следующие реcурсы,
 * которые возвращают готовые HTML-страницы (т.е. это просто Controller'ы):
 * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
 * 1.2 /ui/reader - аналогично 1.1
 * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
 * 1.4* /ui/reader/{id} - страница, где написано имя читателя с идентификатором id и перечислены книги, которые на руках у этого читателя
 *
 * Чтобы шаблонизатор thymeleaf заработал, то нужно добавить зависимость в pom.xml:
 *        <dependency>
 *            <groupId>org.springframework.boot</groupId>
 *            <artifactId>spring-boot-starter-thymeleaf</artifactId>
 *        </dependency>
 */
```