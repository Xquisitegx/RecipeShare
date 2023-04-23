/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created as part of the plan a meal feature where
 * it'll grab all the user that has a plan meal and order it by ascending order.
 *********************************************************************************/

package gbc.comp3095.assignment1.Repository;

import gbc.comp3095.assignment1.Entity.Plan;
import gbc.comp3095.assignment1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer> {
    Set<Plan> findAllByUserAndDateBetweenOrderByDateAsc(User user, LocalDate today, LocalDate sevenDaysAfter);
}
