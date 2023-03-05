package me.ruana.recipeBook.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController; // добавится автоматически при записи @RestController

@RestController // обязательно добавляем для работы

public class FirstController {
    @GetMapping("/") // для получения и обработки запросов, обязательно писать с "/", если версия спринга 3+!!! Адрес в браузере: localhost:8080
    public String  startApp() {
        return "Приложение запущено";
    }
    @GetMapping("/info")// Адрес в браузере будет: localhost:8080/info
    public String  info() {
        String info;
        String fio = "Студент: Руднева А.Е.;";
        String projectName = "Название проекта: Книга рецептов;";
        String creatDate = "Дата создания 23.02.2023 г.;";
        String description = "Описание: Приложение о вкусной и здоровой пище!";
        info = String.join("\n", fio, projectName, creatDate, description); // как сделать с новой строки на странице?
        return info;
    }
}
