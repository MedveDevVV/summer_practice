package autoservice.utils.csv;

import autoservice.model.Identifiable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvImporter {
    public static <T extends Identifiable> List<T> importFromCsv(
            Path filePath,
            Function<String[], T> fieldsToItem) throws IOException {

        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }

        List<T> items = new ArrayList<>();
        List<String> lines;

        try {
            lines = Files.readAllLines(filePath);
        } catch (NoSuchFileException e) {
            throw new IOException("File not found: " + filePath, e);
        } catch (SecurityException e) {
            throw new IOException("No permission to read file: " + filePath, e);
        }

        if (lines.isEmpty()) {
            throw new IOException("File is empty: " + filePath);
        }

        // Remove BOM if present
        if (lines.get(0).startsWith("\uFEFF")) {
            lines.set(0, lines.get(0).substring(1));
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            try {
                String[] fields = line.split(";");
                T item = fieldsToItem.apply(fields);
                items.add(item);
            } catch (Exception e) {
                throw new IOException("Error processing line " + (i + 1) + ": " + line, e);
            }
        }

        return items;
    }
}