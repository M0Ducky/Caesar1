package main.caesar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReadWrite {
    public static String readFile (Path filePath) throws IOException {
        String text  = Files.readString(filePath);
        return text;
    }

    public static void writeFile (File file, String text) throws IOException {
        Files.writeString(file.toPath(), text);
    }
}
