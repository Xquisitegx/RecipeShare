/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created for the user functions where it'll have functions like
 * creating, adding, deleting and updating the user and also favourite meals the user favourite recipe and meal plan
 *********************************************************************************/

package gbc.comp3095.assignment1.Service;

import gbc.comp3095.assignment1.Entity.Event;
import gbc.comp3095.assignment1.Entity.Plan;
import gbc.comp3095.assignment1.Entity.Recipe;
import gbc.comp3095.assignment1.Repository.UserRepository;
import gbc.comp3095.assignment1.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> createUsers(List<User> users){
        return userRepository.saveAll(users);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User findUserByEmailAndUsername(String email, String username) {
        return userRepository.findUserByEmailAndUsername(email, username);
    }

    public User updateUser(User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User newUser = null;

        if (optionalUser.isPresent()) {
            newUser = optionalUser.get();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            newUser.setEmail(user.getEmail());
            newUser.setAddress(user.getAddress());
            newUser.setBirthday(user.getBirthday());

            userRepository.save(newUser);
        } else {
            return new User();
        }

        return newUser;
    }

    public User updateFavoriteRecipe(User user, Recipe recipe) {
        user.getFavoriteRecipes().add(recipe);
        userRepository.save(user);

        return user;
    }

    public User updatePlan(User user, Plan plan) {
        user.getPlans().add(plan);
        userRepository.save(user);

        return user;
    }

    public void deletePlanById(User user, Plan plan, int id) {
        user.getPlans().remove(plan);
        userRepository.save(user);
    }

    public String deleteUserById(Long id) {
        userRepository.deleteById(id);
        return "User has been deleted.";
    }

    public User addEvent(User user, Event event) {
        user.getEvents().add(event);
        userRepository.save(user);

        return user;
    }
    public void deleteEventById(User user,  Event event, int id) {
        user.getEvents().remove(event);
        userRepository.save(user);
    }

}
