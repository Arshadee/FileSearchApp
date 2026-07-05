package org.filesearch;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class to start the application
 *
 * @version 1.0
 * @since 1.0
 */
public class FileSeachApplication {
    
    public static void main(String[] args) {

        // INSERT THIS INSTEAD:
        File configFile = null;
        if (args.length > 0 && args[0] != null && !args[0].trim().isEmpty()) {
            configFile = new File(args[0]);
        }

        if (configFile == null || !configFile.exists() || !configFile.isFile()) {

            File templateFile = new File("search-config-template.json");

            System.err.println("=======================================================================");
            System.err.println(" WARNING: No search configuration file selected (or incorrect format).");
            System.err.println("=======================================================================");
            System.err.println(" Action Required: Please specify a path to a valid JSON configuration file.");
            System.err.println(" Example: java -jar search-tool.jar C:\\path\\to\\search-configDyn.json\n");
            System.err.println(" Notice: A fresh blueprint template has been generated at:");
            System.err.println("         " + templateFile.getAbsolutePath()); // <-- EXPLICIT ABSOLUTE PATH
            System.err.println("=======================================================================");

            if (!templateFile.exists()) {
                try (FileWriter writer = new FileWriter(templateFile)) {
                    String templateJson = "{\n" +
                            "  \"root\": \"C:\\\\Change\\\\This\\\\To\\\\Your\\\\Target\\\\Directory\",\n" +
                            "  \"searchType\": \"AND\",\n" +
                            "  \"inclFileNames\": false,\n" +
                            "  \"inclTxtFileContent\": true,\n" +
                            "  \"noCase\": true,\n" +
                            "  \"txtFileTypes\": [\".txt\", \".java\", \".xml\", \".properties\"],\n" +
                            "  \"exclusionList\": [\"windows\", \"system\", \"target\", \".m2\"],\n" +
                            "  \"extentionsToExclude\": [\".zip\", \".lnk\"],\n" +
                            "  \"queries\": [],\n" +
                            "  \"keyWordItems\": [\n" +
                            "    { \"value\": \"exampleTermA\", \"include\": true },\n" +
                            "    { \"value\": \"exampleTermB\", \"include\": true }\n" +
                            "  ],\n" +
                            "  \"formula\": \"$0 && $1\"\n" +
                            "}";
                    writer.write(templateJson);
                    writer.flush();
                } catch (IOException e) {
                    System.err.println("Failed to write template file: " + e.getMessage());
                }
            }
            System.exit(1);
        }

        System.out.println("Config argument detected: " + configFile.getAbsolutePath());
        AutoUIConfigDyn autoUIConfig = new AutoUIConfigDyn(configFile.getAbsolutePath());
        autoUIConfig.executeConfig();




//       UIConfig UIConfig = new UIConfig();
//       UIConfig.executeConfig();

//        AutoUIConfig autoUIConfig = new AutoUIConfig();
//        autoUIConfig.executeConfig();
    }
}
