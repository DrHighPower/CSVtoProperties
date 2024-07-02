# CSV to Properties File Converter

## Overview
This project provides a Java application that processes a CSV file and writes its contents into a properties file.
The application reads command line arguments to specify the CSV file path, the delimiter used in the CSV file, and the output properties file path. 
It then parses the CSV file, extracts key-value pairs, and writes them to the properties file.

## Usage
### Command Line Arguments

The application accepts the following command line arguments:

- `--csv` (or `-c`, `-C`, `--CSV`): Path to the input CSV file.
- `--delimiter` (or `-d`, `-D`, `--DELIMITER`): Delimiter used in the CSV file.
- `--output` (or `-o`, `-O`, `--OUTPUT`): Path to the output properties file.

If these arguments are not provided, the application uses the following default values:

- CSV file: `src/main/resources/input.csv`
- Delimiter: `,`
- Properties file: `src/main/resources/output.properties`

### Example Command

```sh
java -jar CSVtoProperties.jar --csv path/to/input.csv --delimiter "," --output path/to/output.properties
```