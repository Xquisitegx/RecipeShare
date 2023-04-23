/*********************************************************************************
* Project: RecipeShare
* Assignment: Assignment #1
* Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
* Student Number: 101325908, 100882851, 101277278, 101337015
* Date: October 23rd, 2022
* Description: It fetches all data from json files and parse it. The main purpose
* of it is to insert default data when we run the application.
*********************************************************************************/

package gbc.comp3095.assignment1.Utils;

import gbc.comp3095.assignment1.Entity.Ingredient;
import gbc.comp3095.assignment1.Entity.Recipe;
import gbc.comp3095.assignment1.Entity.Role;
import gbc.comp3095.assignment1.Entity.User;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
public class JsonParser {
    private List<User> defaultUsers = new ArrayList<>();
    private List<Recipe> defaultRecipes = new ArrayList<>();

    public JsonParser() {
    }

    public void prepareDefaultUsers() {
        try {
            // Getting JSON file from resource directory
            File file = ResourceUtils.getFile("classpath:default_data/user_data.json");
            FileReader fileReader = new FileReader(file);
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(fileReader);

            // JSON parsing
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                Iterator iterator2 = ((Map) iterator.next()).entrySet().iterator();
                List<String> information = new ArrayList<>();

                while (iterator2.hasNext()) {
                    Map.Entry pair = (Map.Entry) iterator2.next();
                    information.add(pair.getValue().toString());
                }

                defaultUsers.add(new User().populateInfo(information));
            }

            for (int i = 0; i < 5; ++i) {
                defaultUsers.get(i).setId(i + 1L);

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(defaultUsers.get(i).getPassword());
                defaultUsers.get(i).setPassword(encodedPassword);
                defaultUsers.get(i).getRoles().add(new Role(1));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void prepareDefaultRecipes() {
        try {
            // Getting JSON file from resource directory
            File file = ResourceUtils.getFile("classpath:default_data/recipe_data.json");
            FileReader fileReader = new FileReader(file);
            JSONArray jsonArray = (JSONArray) new JSONParser().parse(fileReader);

            // JSON Parsing
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                Iterator iterator2 = ((Map) iterator.next()).entrySet().iterator();
                List<String> recipeInformation = new ArrayList<>();

                while (iterator2.hasNext()) {
                    Map.Entry pair = (Map.Entry) iterator2.next();
                    recipeInformation.add(pair.getValue().toString());

                    if (Objects.equals(pair.getKey().toString(), "ingredients")) {
                        Recipe recipe = new Recipe().populateInfo(recipeInformation);

                        JSONArray jsonArray1 = (JSONArray) pair.getValue();
                        Iterator iterator3 = jsonArray1.iterator();
                        while (iterator3.hasNext()) {
                            Iterator ingredients = ((Map) iterator3.next()).entrySet().iterator();
                            List<String> ingredientInformation = new ArrayList<>();

                            while (ingredients.hasNext()) {
                                Map.Entry ingredientPair = (Map.Entry) ingredients.next();
                                ingredientInformation.add(ingredientPair.getValue().toString());
                            }

                            recipe.getIngredients().add(new Ingredient().populateInfo(ingredientInformation));
                        }
                        defaultRecipes.add(recipe);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void populateImageToRecipes() {
        List<byte[]> images = new ImageParser().getImageBytes();
        for (int i = 0; i < images.size(); ++i) {
            byte[] encodedFile = Base64.getEncoder().encode(images.get(i));
            String encodedFileString = new String(encodedFile, StandardCharsets.UTF_8);
            defaultRecipes.get(i).setImageFile(encodedFileString);
        }
    }

    public void populateUserToRecipe(List<User> users) {
        Random random = new Random();
        for (Recipe recipe : defaultRecipes) {
            recipe.setUser(users.get(random.nextInt(users.size())));
        }
    }
}