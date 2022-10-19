package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    public static String fileToString(String file_path) throws IOException {
        Path path = Path.of(file_path);
        return Files.readString(path);
    }

    public static void appendStringToFile(String content, String file_path) {
        try {
            Files.write(Path.of(file_path), content.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void createDir(String path_dir) {
        File testDir = new File(path_dir);
        if (!testDir.exists() || !testDir.isDirectory()) {
            boolean res = testDir.mkdir();
            if(!res) {
                System.err.println("Folder could not be created");
            }
        }
    }

    public static void createFile(String path_file) throws IOException {
        File file = new File(path_file);
        if (!file.exists() || !file.isFile()) {
            if (!file.createNewFile()) {
                System.err.println("File could not be created");
            }
        }
    }
}
