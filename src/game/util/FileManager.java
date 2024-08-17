package game.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    // get Files in folder String[] path
    public static String[] getFiles(String directory) {
        directory = directory.replace("\\", "/");
        try {
            return Files.walk(Paths.get(directory))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .map(path -> path.replace("\\", "/"))
                    .toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}