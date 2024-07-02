package com.HighPower;

import java.util.HashMap;
import java.util.Map;

/**
 * The ArgumentParser class is responsible for parsing command line arguments and providing values for specified flags.
 * <p>
 * This class supports flag aliases and default values. It maps flags and their aliases to the values provided in the
 * command line arguments, or returns default values if a flag is not explicitly provided.
 * </p>
 */
public class ArgumentParser {
    // Map to store flag mappings to values
    private final Map<String, String> argMap = new HashMap<>();
    // Map to store default values for flags
    private final Map<String, String> defaultValues;

    /**
     * Constructs an ArgumentParser with the provided command line arguments, flag aliases, and default values.
     *
     * @param args          The command line arguments
     * @param flagAliases   A map of main flags to their aliases
     * @param defaultValues A map of main flags to their default values
     */
    public ArgumentParser(String[] args, Map<String, String[]> flagAliases, Map<String, String> defaultValues) {
        this.defaultValues = defaultValues;

        // Create a combined map of all aliases to their main flags
        Map<String, String> aliasMap = new HashMap<>();
        for (Map.Entry<String, String[]> entry : flagAliases.entrySet()) {
            String mainFlag = entry.getKey();
            aliasMap.put(mainFlag, mainFlag);

            for (String alias : entry.getValue()) {
                aliasMap.put(alias, mainFlag);
            }
        }

        // Iterate through the arguments
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            // Check if the argument is a recognized flag or alias
            if (aliasMap.containsKey(arg)) {
                // Ensure there is a value following the flag
                if (i + 1 < args.length) {
                    String mainFlag = aliasMap.get(arg);
                    argMap.put(mainFlag, args[i + 1]);
                    i++; // Skip the value for the next iteration
                } else {
                    System.err.println("Error: No value provided for flag " + arg);
                }
            }
        }
    }

    /**
     * Retrieves the value associated with the specified flag.
     * <p>
     * If the flag was not provided in the command line arguments, the default value for the flag is returned.
     * </p>
     *
     * @param flag The flag whose value is to be retrieved
     * @return The value associated with the flag, or the default value if the flag was not provided
     */
    public String getValue(String flag) {
        return argMap.getOrDefault(flag, defaultValues.get(flag));
    }

}
