/*********************************************************************************
* Project: RecipeShare
* Assignment: Assignment #1
* Author(s): Seunghun Yim, Danny Nguyen, Yoonhee Kim, Elizaveta Vygovskaia
* Student Number: 101325908, 100882851, 101277278, 101337015
* Date: October 23rd, 2022
* Description: It fetches all data from image files. The main purpose
* of it is to insert default images of recipes when we run the application.
*********************************************************************************/

package gbc.comp3095.assignment1.Utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageParser {
    private byte[] defaultImage;
    private List<byte[]> imageBytes = new ArrayList<>();

    public ImageParser() {
        try {
            // Setting default image
            File file = ResourceUtils.getFile("classpath:default_images/default.png");
            FileInputStream fileInputStream = new FileInputStream(file);
            defaultImage = fileInputStream.readAllBytes();

            // Setting default images for testing
            for (int i = 1; i <= 4; i++) {
                file = ResourceUtils.getFile("classpath:default_images/image" + i + ".jpg");
                fileInputStream = new FileInputStream(file);
                imageBytes.add(fileInputStream.readAllBytes());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<byte[]> getImageBytes() {
        return imageBytes;
    }

    public byte[] getDefaultImage() {
        return defaultImage;
    }
}
