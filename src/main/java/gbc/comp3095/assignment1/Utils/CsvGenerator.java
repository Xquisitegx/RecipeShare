/*********************************************************************************
 * Project: RecipeShare
 * Assignment: Assignment #2
 * Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
 * Student Number: 101325908, 100882851, 101277278, 101337015
 * Date: December 4th, 2022
 * Description: It is a custom class that creates a csv file that includes ingredient information
 *********************************************************************************/

package gbc.comp3095.assignment1.Utils;

import gbc.comp3095.assignment1.Entity.Ingredient;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvGenerator {
    public void writeIngredientsToCSV(List<Ingredient> ingredients, Writer writer) {
        try {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            for (Ingredient ingre : ingredients) {
                printer.printRecord(
                        ingre.getName(),
                        ingre.getAmount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
