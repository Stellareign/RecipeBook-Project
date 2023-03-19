package me.ruana.recipeBook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // обязательно добавляем для работы
@Tag(name = "Стартовая информация", description = "Первый контроллер, информация о создании проекта.")

public class FirstController {
    @GetMapping("/")
    // для получения и обработки запросов, обязательно писать с "/", если версия спринга 3+!!! Адрес в браузере: localhost:8080
    @Operation(summary = "Проверка запуска приложения",
            description = "Выводит сообщение о запуске приложения")
    public String startApp() {
        return "Приложение запущено";
    }

    @GetMapping("/info")// Адрес в браузере будет: localhost:8080/info
    @Operation(summary = "Информация о приложении",
            description = "Выводит данные о разработчике и краткое описание приложения")
    public String info() {
        String info;
        String fio = "Студент: Руднева А.Е.;";
        String projectName = "Название проекта: Книга рецептов;";
        String creatDate = "Дата создания 23.02.2023 г.;";
        String description = "Описание: Приложение о вкусной и здоровой пище! - учебный проект";
        info = String.join("\n", fio, projectName, creatDate, description); // как сделать с новой строки на странице?
        return info;
    }
}
