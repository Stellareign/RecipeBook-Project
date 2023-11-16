package me.ruana.recipeBook;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@JsonIgnoreProperties
public class RecipeBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipeBookApplication.class, args);
    } // запуск в базовом виде

}
