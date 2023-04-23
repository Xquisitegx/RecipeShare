/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: It is a controller class for Event entity.
 * All CRUD functions related to event are implemented
 *********************************************************************************/

package gbc.comp3095.assignment1.Controller;

import gbc.comp3095.assignment1.Entity.Event;
import gbc.comp3095.assignment1.Entity.Recipe;
import gbc.comp3095.assignment1.Entity.User;
import gbc.comp3095.assignment1.Service.EventService;
import gbc.comp3095.assignment1.Service.RecipeService;
import gbc.comp3095.assignment1.Service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import gbc.comp3095.assignment1.Repository.EventRepository;

@Controller
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    @GetMapping("/viewEvent")
    public String viewEvent(Model model) {
        // Getting currently logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.getUserByUsername(username);

        model.addAttribute("events", eventService.getEvents(user));
        return "view_events";
    }

    @GetMapping("/addEvent")
    public String addEventGet(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("recipes", recipeService.getRecipes());
        return "add_event";
    }

    @PostMapping("/addEvent")
    public String addEventPost(Event event, @RequestParam("recipeid") int recipeId) {
        // Getting currently logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.getUserByUsername(username);

        // Getting recipe
        Recipe recipe = recipeService.getRecipeById(recipeId);

        event.setRecipe(recipe);
        event.setUser(user);

        userService.addEvent(user, event);

        return "redirect:/viewEvent";
    }

    @GetMapping("/updateEvent/{eventId}")
    public String updateEventGet(Model model,
                                 @PathVariable int eventId,
                                 @ModelAttribute("events") Event updatedEvent) {

        Event event = eventService.getEventById(eventId);

        model.addAttribute("event", event);
        model.addAttribute("recipes", recipeService.getRecipes());

        return "update_event";
    }

    @PostMapping("/updateInfoEvent/{eventId}")
    public String updateEventPost(Event event, @PathVariable int eventId, @RequestParam(name = "recipeid", required = false) Integer recipeId) {

        eventService.updateEvent(event, eventId, recipeId);

        return "redirect:/viewEvent";
    }

    @GetMapping("/deleteEvent/{eventId}")
    public String deletePlan(@PathVariable int eventId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userService.getUserByUsername(username);
        Event event = eventService.getEventById(eventId);

        userService.deleteEventById(user, event, eventId);
        eventService.deleteEvent(eventId);

        return "redirect:/viewEvent";
    }
}
