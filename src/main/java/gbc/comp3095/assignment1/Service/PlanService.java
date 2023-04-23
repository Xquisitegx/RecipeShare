/*********************************************************************************
* Project: RecipeShare
* Assignment: Assignment #1
* Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
* Student Number: 101325908, 100882851, 101277278, 101337015
* Date: October 23rd, 2022
* Description: This Java file is created for the meal plan functions where we have
* functions to delete, create, get all plans or a specific meal plan by ID
*********************************************************************************/

package gbc.comp3095.assignment1.Service;

import gbc.comp3095.assignment1.Entity.Plan;
import gbc.comp3095.assignment1.Entity.User;
import gbc.comp3095.assignment1.Repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public Plan createPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public void deletePlan(int id) {
        planRepository.deleteById(id);
    }

    public Set<Plan> getPlans(User user) {
        LocalDate today = LocalDate.now();
        LocalDate afterWeek = today.plusWeeks(1);
        return planRepository.findAllByUserAndDateBetweenOrderByDateAsc(user, today, afterWeek);
    }

    public Plan getPlanById(int id) {
        return planRepository.findById(id).orElse(null);
    }
}