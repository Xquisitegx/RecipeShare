/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: This Java file is created for the shopping model where all basic
 * information about the shopping where the system will be able to set and or get
 * the required information. The shopping table will be created.
 *********************************************************************************/

package gbc.comp3095.assignment1.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shoppings")
public class Shopping {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne(cascade=CascadeType.ALL)
    Recipe recipe;
    @ManyToOne(cascade=CascadeType.ALL)
    User user;
    @ManyToMany
    List<Ingredient> ingredients = new ArrayList<>();
}