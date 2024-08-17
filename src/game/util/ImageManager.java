// Import necessary classes
package game.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;

// Define a public class called ImageManager
public class ImageManager {

    // Define a public static method called load that takes a String parameter
    // called path and returns a BufferedImage object
    public static BufferedImage load(String path) {
        BufferedImage image = null;
        try {
            // Attempt to read the image from the specified file path
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            // If an IOException occurs, print the stack trace
            e.printStackTrace();
        }
        // Return the loaded image
        return image;
    }

    // Define a public static method called resizeImage that takes three int
    // parameters called width, height, and deg, and a String parameter called
    // imagePath, and returns a BufferedImage object
    public static BufferedImage resizeImage(String imagePath, int width, int height) {
        BufferedImage originalImage = null;
        try {
            // Attempt to read the original image from the specified file path
            originalImage = ImageIO.read(new File(imagePath));
            // Create a new BufferedImage object with the specified width and height
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            // Create a Graphics2D object from the resized image
            Graphics2D g2d = resizedImage.createGraphics();
            // Draw the original image onto the resized image
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            // Dispose of the Graphics2D object
            g2d.dispose();
            // Return the resized image
            return resizedImage;
        } catch (IOException e) {
            // If an IOException occurs, print the stack trace
            e.printStackTrace();
        }
        // If an exception occurred, return the original image
        return originalImage;
    }

    // Define a public static method called resizeImage that takes three int
    // parameters called width, height, and deg, and a String parameter called
    // imagePath, and returns a BufferedImage object
    public static BufferedImage resizeImage(String imagePath, int width, int height, int deg) {
        BufferedImage originalImage = null;
        try {
            // Attempt to read the original image from the specified file path
            originalImage = ImageIO.read(new File(imagePath));
            // Create a new BufferedImage object with the specified width and height
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            // Create a Graphics2D object from the resized image
            Graphics2D g2d = resizedImage.createGraphics();
            // Rotate the Graphics2D object by the specified number of degrees around the
            // center of the image
            g2d.rotate(Math.toRadians(deg), width / 2, height / 2);
            // Draw the original image onto the resized image
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            // Dispose of the Graphics2D object
            g2d.dispose();
            // Return the resized image
            return resizedImage;
        } catch (IOException e) {
            // If an IOException occurs, print the stack trace
            e.printStackTrace();
        }
        // If an exception occurred, return the original image
        return originalImage;
    }

    // Define a public static method called resizeImage that takes four int
    // parameters called width, height, deg, and alpha, and a String parameter
    // called imagePath, and returns a BufferedImage object
    public static BufferedImage resizeImage(String imagePath, int width, int height, int deg, int alpha) {
        BufferedImage originalImage = null;
        try {
            // Attempt to read the original image from the specified file path
            originalImage = ImageIO.read(new File(imagePath));
            // Create a new BufferedImage object with the specified width and height
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            // Create a Graphics2D object from the resized image
            Graphics2D g2d = resizedImage.createGraphics();
            // Rotate the Graphics2D object by the specified number of degrees around the
            // center of the image
            g2d.rotate(Math.toRadians(deg), width / 2, height / 2);
            // Set the alpha composite of the Graphics2D object to the specified value
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
            // Draw the original image onto the resized image
            g2d.drawImage(originalImage, 0, 0, width, height, null);
            // Dispose of the Graphics2D object
            g2d.dispose();
            // Return the resized image
            return resizedImage;
        } catch (IOException e) {
            // If an IOException occurs, print the stack trace
            e.printStackTrace();
        }
        // If an exception occurred, return the original image
        return originalImage;
    }
}
