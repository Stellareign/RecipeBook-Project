package me.ruana.recipeBook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; // добавится автоматически при записи @RestController

@RestController // обязательно добавляем для работы
public class FirstController {
    @GetMapping("/") // для получения и обработки запросов, обязательно писать с "/", если версия спринга 3+!!! Адрес в браузере: localhost:8080
    public String  helloWorld() {
        return "Приложение запущено";
    }
    @GetMapping("/path/to/page")// Адрес в браузере будет: localhost:8080/path/to/page
    public String  page(@RequestParam String page) {
        return "Page: " + page; // можно задавать значение для отображения на странице уже прямо в адресной строке
        // после ?: localhost:8080/path/to/page?page=12122. В браузере можно обрабатывать только гет-запросы
        // вывод на странице: Page: 12122
    }
}
