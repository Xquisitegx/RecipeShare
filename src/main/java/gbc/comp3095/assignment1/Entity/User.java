/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #1
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: October 23rd, 2022
 * Description: This Java file is created for the User Model where the table for User
 * is created and a merge of tables will be established, this User class will have
 * the getters and setters required for the main purpose of our project.
 *********************************************************************************/

package gbc.comp3095.assignment1.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "password")
        })
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String address;
    private LocalDate birthday;

    @ManyToMany(cascade=CascadeType.ALL)
    private Set<Recipe> favoriteRecipes;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<Plan> plans;

    @OneToMany(cascade=CascadeType.ALL)
    private Set<Event> events;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User populateInfo(List<String> info) {
        this.firstName = info.get(0);
        this.password = info.get(1);
        this.address = info.get(2);
        this.email = info.get(3);
        this.username = info.get(4);
        this.lastName = info.get(5);
        this.birthday = LocalDate.now();

        return this;
    }
}
