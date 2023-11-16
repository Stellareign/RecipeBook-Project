package me.ruana.recipeBook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.ruana.recipeBook.dto.RecipeDTO;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String title;
    private String numberOfServings;
    private double cookingTime;
    private String measureTime;
    private List<Ingredients> ingredientsList;
    private List<String> cookingStepsList;
    private static RecipeDTO RECIPEDTO;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(); // конструктор StringBuilder используем для нормального вывода содержимого (быстрее чем StringBuffer, но менее безопасный)
        sb.append(title).append(". \n\n") // append присоединяет к строке подстроку
                .append("Количество порций: ").append(numberOfServings).append("\n")
                .append("Время приготовления: ").append(cookingTime).append(measureTime).append("\n\n")
                .append("Ингредиенты:\n");
        for (Ingredients ingredient : ingredientsList) {
            sb.append(ingredient.toString()).append("\n");
        }
        sb.append("\nКак приготовить:\n");
        for (String step : cookingStepsList) {
            sb.append(step).append("\n");
        }
        return sb.toString().replace("[", "").replace("]", ""); // убираем скобки - заменяем на "пустоту"
    }

}

