package com.HighPower;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The Main class processes a CSV file and writes its contents into a properties file.
 * <p>
 * The class reads command line arguments to specify the CSV file path, the delimiter used in the CSV file,
 * and the output properties file path. It then parses the CSV file, extracts key-value pairs, and writes them
 * to the properties file.
 * </p>
 */
public class Main {
    private static final String CSV_FILE = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "input.csv";
    private static final String COMMA_DELIMITER = ",";
    private static final String PROPERTIES_FILE = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "output.properties";

    private static final String CSV_FLAG = "--csv";
    private static final String DELIMITER_FLAG = "--delimiter";
    private static final String PROPERTIES_FLAG = "--output";

    /**
     * The main method that serves as the entry point of the application.
     * <p>
     * This method parses command line arguments, reads a CSV file, and writes the contents to a properties file.
     * </p>
     *
     * @param args Rhe command line arguments
     */
    public static void main(String[] args) {
        // Define flag aliases
        ArgumentParser parser = getArgumentParser(args);

        try {
            // Read CSV file
            List<List<String>> records = Files.readAllLines(Paths.get(parser.getValue(CSV_FLAG)))
                    .stream()
                    .map(line -> Arrays.asList(line.split(parser.getValue(DELIMITER_FLAG))))
                    .toList();

            // Write properties file
            try (OutputStream output = new FileOutputStream(parser.getValue(PROPERTIES_FLAG))) {
                Properties prop = new Properties();

                // Add all the data from the CSV file
                for (List<String> data : records) {
                    if (data.size() >= 2) {
                        prop.setProperty(data.get(0), data.get(1));
                    } else {
                        System.err.println("Warning: Skipping malformed CSV line: " + data);
                    }
                }

                // Add info to file
                prop.store(output, null);
            }
        } catch (IOException e) {
            System.err.println("Error: Failed to process the files. " + e.getMessage());
        }
    }

    /**
     * Returns an {@link ArgumentParser} initialized with the provided command line arguments, flag aliases, and default values.
     *
     * @param args the command line arguments
     * @return An {@link ArgumentParser} initialized with the provided arguments, flag aliases, and default values
     */
    private static ArgumentParser getArgumentParser(String[] args) {
        Map<String, String[]> flagAliases = Map.of(
                CSV_FLAG, new String[]{"-c", "-C", "--CSV"},
                DELIMITER_FLAG, new String[]{"-d", "-D", "--DELIMITER"},
                PROPERTIES_FLAG, new String[]{"-o", "-O", "--OUTPUT"}
        );

        // Define default values for the flags
        Map<String, String> defaultValues = Map.of(
                CSV_FLAG, CSV_FILE,
                DELIMITER_FLAG, COMMA_DELIMITER,
                PROPERTIES_FLAG, PROPERTIES_FILE
        );

        // Parse the arguments
        return new ArgumentParser(args, flagAliases, defaultValues);
    }
}