/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: This Java file is created as part of the shopping model where users will
 * be able to get a list of shopping objects by user object.
 *********************************************************************************/

package gbc.comp3095.assignment1.Repository;

import gbc.comp3095.assignment1.Entity.Shopping;
import gbc.comp3095.assignment1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingRepository extends JpaRepository<Shopping, Integer> {
    public List<Shopping> findAllByUser(User user);
}
