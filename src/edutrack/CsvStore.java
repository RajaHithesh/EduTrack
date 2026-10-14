package edutrack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvStore {
    private static final String DATA_DIR = "data";

    public static void ensureDataDirectory() {
        Path dir = Paths.get(DATA_DIR);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create data directory: " + e.getMessage(), e);
            }
        }
    }

    public static void ensureFile(String fileName, String[] headers) {
        ensureDataDirectory();
        Path path = Paths.get(DATA_DIR, fileName);
        if (Files.exists(path)) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            writer.write(joinCsv(headers));
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize CSV file: " + fileName, e);
        }
    }

    public static <T> List<T> readRecords(String fileName, Function<String[], T> mapper) {
        Path path = Paths.get(DATA_DIR, fileName);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        List<T> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            boolean headSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                if (!headSkipped) {
                    headSkipped = true;
                    continue;
                }
                T item = mapper.apply(parseCsvLine(line));
                if (item != null) {
                    records.add(item);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read CSV file: " + fileName, e);
        }
        return records;
    }

    public static void writeRecords(String fileName, String[] headers, List<String[]> rows) {
        ensureDataDirectory();
        Path path = Paths.get(DATA_DIR, fileName);
        Path temp = Paths.get(DATA_DIR, fileName + ".tmp");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp.toFile()))) {
            writer.write(joinCsv(headers));
            writer.newLine();
            for (String[] row : rows) {
                writer.write(joinCsv(row));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write temporary CSV file: " + fileName, e);
        }
        try {
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to replace CSV file: " + fileName, e);
        }
    }

    public static String joinCsv(String[] values) {
        if (values == null) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) builder.append(',');
            builder.append(escapeCsv(values[i] == null ? "" : values[i]));
        }
        return builder.toString();
    }

    private static String escapeCsv(String value) {
        if (value == null) return "";
        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
            return '"' + escaped + '"';
        }
        return escaped;
    }

    public static String[] parseCsvLine(String line) {
        if (line == null) return new String[0];
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }
        values.add(current.toString());
        return values.toArray(new String[0]);
    }
}
