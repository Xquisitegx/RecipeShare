/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created for the Recipe model where a table labelled
 * as Recipe will be created and a one-to-many and a many to one relationship will be
 * created.
 *********************************************************************************/

package gbc.comp3095.assignment1.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private String description;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private String instruction;
    private LocalDate createdDate;

    @Lob
    @Column(name = "image", length = Integer.MAX_VALUE)
    private String imageFile;

    @ManyToOne
    private User user;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe populateInfo(List<String> info) {
        this.instruction = info.get(0);
        this.name = info.get(1);
        this.description = info.get(2);

        return this;
    }
}