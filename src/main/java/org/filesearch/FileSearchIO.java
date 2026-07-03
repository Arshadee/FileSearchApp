package org.filesearch;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileSearchIO {
    
    public static void writeToFile(List<String> results, String filePath) {
        Path output = Paths.get(filePath);
        try {
            Files.write(output, results);
            System.out.println(output.toFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
