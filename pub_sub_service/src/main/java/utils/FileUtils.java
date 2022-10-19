package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static String fileToString(String file_path) throws IOException {
        Path path = Path.of(file_path);
        return Files.readString(path);
    }
}
