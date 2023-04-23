/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created for the recipe functions where we have
 * functions to delete, create, get all plans or a specific recipe by name or ID
 *********************************************************************************/

package gbc.comp3095.assignment1.Service;

import gbc.comp3095.assignment1.Entity.Ingredient;
import gbc.comp3095.assignment1.Entity.User;
import gbc.comp3095.assignment1.Repository.RecipeRepository;
import gbc.comp3095.assignment1.Entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(Recipe recipe) {
        recipe.setCreatedDate(LocalDate.now());
        return recipeRepository.save(recipe);
    }

    public List<Recipe> createRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            recipe.setCreatedDate(LocalDate.now());
        }
        return recipeRepository.saveAll(recipes);
    }

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public Ingredient getIngredientById(int recipeId, int ingredientId) {
        List<Ingredient> ingredients = Objects.requireNonNull(recipeRepository.findById(recipeId).orElse(null)).getIngredients();

        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == ingredientId) return ingredient;
        }

        return null;
    }

    public List<Recipe> getRecipeByUserId(Long userId) {
        List<Recipe> newRecipe = new ArrayList<>();
        List<Recipe> recipes = recipeRepository.findAll();

        for (Recipe recipe: recipes) {
            User user = recipe.getUser();
            if (user != null && Objects.equals(user.getId(), userId)) {
                newRecipe.add(recipe);
            }
        }

        return newRecipe;
    }

    public List<Recipe> getRecipesByName(String name) {
        List<Recipe> recipes = getRecipes();
        List<Recipe> returningRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase(Locale.ROOT).contains(name.toLowerCase(Locale.ROOT))) {
                returningRecipes.add(recipe);
            }
        }

        return returningRecipes;
    }

    public void updateRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    public void deleteRecipeById(int id) {
        recipeRepository.deleteById(id);
    }
}
