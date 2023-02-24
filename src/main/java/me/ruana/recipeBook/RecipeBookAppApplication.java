package me.ruana.recipeBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // обязательно добавляем для работы
public class RecipeBookAppApplication {
public static void main(String[] args){
	SpringApplication.run(RecipeBookAppApplication.class, args);}

	@GetMapping("/") // для получения и обработки запросов, обязательно писать с "/", если версия спринга 3+!!! Адрес в браузере: localhost:8080
	public String  helloWorld() {
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