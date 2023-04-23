/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: This Java file is created as part of the event model where users will
 * be able to get list of event objects by user object.
 *********************************************************************************/

package gbc.comp3095.assignment1.Repository;

import gbc.comp3095.assignment1.Entity.Event;
import gbc.comp3095.assignment1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Set<Event> findAllByUser(User user);
    Set<Event> findAllByUserAndDateBetweenOrderByDateAsc(User user, LocalDate today, LocalDate sevenDaysAfter);

}
