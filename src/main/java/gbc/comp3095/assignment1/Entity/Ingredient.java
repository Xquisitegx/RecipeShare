/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created for the ingredient model where all basic
 * information about the ingredients where the system will be able to set and or get
 * the required information. The table ingredients will be created.
 *********************************************************************************/

package gbc.comp3095.assignment1.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String amount;

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public Ingredient populateInfo(List<String> info) {
        this.amount = info.get(0);
        this.name = info.get(1);

        return this;
    }
}
