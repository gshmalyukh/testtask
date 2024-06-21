package com.gshmalyukh.demo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PrimitiveFileWriter {
    public static void writeToFile(String content, String fileName){

        // Define the file path
        Path filePath = Paths.get(fileName);

        try {
            // Write the content to the file
            Files.writeString(filePath, content);

            System.out.println("Content has been written to the file.");

        } catch (IOException e) {
            System.err.println("Failed to write content to file: " + e.getMessage());
        }
    }

}
