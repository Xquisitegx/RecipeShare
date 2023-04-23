/*********************************************************************************
* Project: RecipeShare
* Assignment: Assignment #1
* Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
* Student Number: 101325908, 100882851, 101277278, 101337015
* Date: October 23rd, 2022
* Description: It is a custom exception class that I can determine the error message
*********************************************************************************/

package gbc.comp3095.assignment1.Utils;

public class CustomException extends RuntimeException {
    public CustomException() {}

    public CustomException(String message) {
        super(message);
    }
}
