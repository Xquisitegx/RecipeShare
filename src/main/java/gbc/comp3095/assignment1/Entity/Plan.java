/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created for the Plan model where the table and
 * many to many relationship between the recipe and user table will be merged in order
 * to create the plan meal table.
 *********************************************************************************/

package gbc.comp3095.assignment1.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue
    private int id;
    private String mealTime;
    private LocalDate date;

    @ManyToOne
    Recipe recipe;
    @ManyToOne
    User user;
}
