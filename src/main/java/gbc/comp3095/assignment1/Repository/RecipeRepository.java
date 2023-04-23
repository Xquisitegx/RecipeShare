/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created as part of the recipe model where users will
 * be able to search for a specific recipe by letters or words contained in the recipe
 * name.
 *********************************************************************************/

package gbc.comp3095.assignment1.Repository;

import gbc.comp3095.assignment1.Entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    public List<Recipe> findByNameContaining(String name);
}
