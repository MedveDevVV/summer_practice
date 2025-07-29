package autoservice.utils.csv;

import autoservice.model.Identifiable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class CsvExporter {
    public static <T extends Identifiable> void exportToCsv(
            List<T> items,
            Path filePath,
            Function<T, String[]> itemToFields) throws IOException {

        if (filePath == null) {
            throw new IllegalArgumentException("File path cannot be null");
        }
        if (items == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }

        try {
            // Создаем родительские директории, если их нет
            Path parentDir = filePath.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write("\uFEFF"); // BOM для UTF-8
                for (T item : items) {
                    try {
                        String[] fields = itemToFields.apply(item);
                        writer.write(String.join(";", fields));
                        writer.newLine();
                    } catch (Exception e) {
                        throw new IOException("Failed to convert item to CSV fields: " + item, e);
                    }
                }
            }
        } catch (SecurityException e) {
            throw new IOException("No permission to write to file: " + filePath, e);
        }
    }
}