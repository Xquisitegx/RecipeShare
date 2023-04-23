/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: It is a controller class for shopping entity.
 * All CRUD functions related to shopping list are implemented
 *********************************************************************************/

package gbc.comp3095.assignment1.Controller;

import gbc.comp3095.assignment1.Entity.Ingredient;
import gbc.comp3095.assignment1.Entity.Recipe;
import gbc.comp3095.assignment1.Entity.Shopping;
import gbc.comp3095.assignment1.Entity.User;
import gbc.comp3095.assignment1.Service.RecipeService;
import gbc.comp3095.assignment1.Service.ShoppingService;
import gbc.comp3095.assignment1.Service.UserService;
import gbc.comp3095.assignment1.Utils.CsvGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShoppingController {
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private CsvGenerator csvGenerator;

    @GetMapping("/viewMyShoppingLists")
    public String viewShoppingLists(Model model) {
        // Getting currently logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.getUserByUsername(username);

        model.addAttribute("shoppings", shoppingService.getShoppingsByUser(user));

        return "view_shoppings";
    }

    @GetMapping("/viewMyShoppingLists/{id}")
    public String viewShoppingList(Model model, @PathVariable int id) {
        Shopping shopping = shoppingService.getShoppingById(id);
        List<Ingredient> shoppingIngredients = shopping.getIngredients();
        List<Ingredient> recipeIngredients = shopping.getRecipe().getIngredients();
        List<Ingredient> ingredients = new ArrayList<>();

        for (Ingredient rIngredient : recipeIngredients) {
            boolean isPresent = shoppingIngredients.stream().anyMatch(ingredient -> ingredient.getId() == rIngredient.getId());

            if (!isPresent) ingredients.add(rIngredient);
        }

        model.addAttribute("shopping", shopping);
        model.addAttribute("recipeIngredients", ingredients);

        return "view_shopping";
    }

    @PostMapping("/viewMyShoppingLists/{shoppingId}")
    public String addIngredientToShoppingList(@PathVariable int shoppingId, @RequestParam("ingredientId") int ingredientId) {
        Shopping shopping = shoppingService.getShoppingById(shoppingId);
        List<Ingredient> shoppingIngredients = shopping.getIngredients();
        List<Ingredient> recipeIngredients = shopping.getRecipe().getIngredients();

        // adding new ingredient to shopping ingredients
        Ingredient newIngredient = null;
        for (Ingredient rIngredient : recipeIngredients) {
            if (rIngredient.getId() == ingredientId) newIngredient = rIngredient;
        }
        shoppingIngredients.add(newIngredient);
        shopping.setIngredients(shoppingIngredients);

        // update
        shoppingService.updateShopping(shopping);

        return "redirect:/viewMyShoppingLists/" + shoppingId;
    }

    @GetMapping("/viewMyShoppingLists/{id}/export")
    public void exportShoppingList(
            HttpServletResponse response,
            @PathVariable int id) throws IOException
    {
        Shopping shopping = shoppingService.getShoppingById(id);

        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"shopping_list.csv\"");

        csvGenerator.writeIngredientsToCSV(shopping.getIngredients(), response.getWriter());
    }

    @GetMapping("/viewMyShoppingLists/{shoppingId}/deleteIngredient/{ingredientId}")
    public String deleteIngredientOfShoppingList(
            @PathVariable int shoppingId,
            @PathVariable int ingredientId)
    {
        shoppingService.deleteIngredientByIds(shoppingId, ingredientId);

        return "redirect:/viewMyShoppingLists/" + shoppingId;
    }

    @GetMapping("/recipe/{recipeId}/addShopping")
    public String addShopping(
            Model model,
            @PathVariable int recipeId,
            @ModelAttribute("recipe") Recipe previousRecipe) {
        if (previousRecipe.getId() != 0) {
            model.addAttribute("ingredients", previousRecipe.getIngredients());
        } else {
            Recipe recipe = recipeService.getRecipeById(recipeId);
            model.addAttribute("ingredients", recipe.getIngredients());
        }

        return "add_shopping";
    }

    @PostMapping("/recipe/{recipeId}/addShopping")
    public String addIngredientToShoppingList(
            @PathVariable int recipeId,
            @RequestParam(value = "ingredients", required = false) List<String> selectedIngredients)
    {
        if (selectedIngredients == null) {
            return "redirect:/recipe/" + recipeId + "/addShopping?error";
        }

        // Getting currently logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        List<Ingredient> ingredients = new ArrayList<>();
        for (String id : selectedIngredients) {
            ingredients.add(recipeService.getIngredientById(recipeId, Integer.parseInt(id)));
        }

        Shopping shopping = new Shopping();
        shopping.setUser(userService.getUserByUsername(username));
        shopping.setRecipe(recipeService.getRecipeById(recipeId));
        shopping.setIngredients(ingredients);
        shoppingService.createShopping(shopping);

        return "redirect:/viewMyShoppingLists";
    }
}
