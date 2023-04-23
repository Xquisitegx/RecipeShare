/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: This Java file is created for the Event functions where it'll have functions like
 * getting, creating, deleting and updating the shopping
 *********************************************************************************/

package gbc.comp3095.assignment1.Service;

import gbc.comp3095.assignment1.Entity.Event;
import gbc.comp3095.assignment1.Entity.Recipe;
import gbc.comp3095.assignment1.Entity.User;
import gbc.comp3095.assignment1.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private RecipeService recipeService;

    public void deleteEvent(int id) {
        eventRepository.deleteById(id);
    }

    public Set<Event> getEvents(User user) {
        return eventRepository.findAllByUser(user);
    }

    public Event getEventById(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event updateEvent(Event event, int eventId, Integer recipeId){

        Event currentEvent = getEventById(eventId);
        Recipe recipe;
        if(recipeId == null){
            recipe = recipeService.getRecipeById(currentEvent.getRecipe().getId());
        }
        else{
            recipe = recipeService.getRecipeById(recipeId);
        }

        currentEvent.setDate(event.getDate());
        currentEvent.setRecipe(recipe);
        currentEvent.setEventName(event.getEventName());
        currentEvent = eventRepository.save(event);

        return currentEvent;
    }

}