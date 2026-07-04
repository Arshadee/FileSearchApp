package org.filesearch;


/**
 * Main class to start the application
 *
 * @version 1.0
 * @since 1.0
 */
public class FileSeachApplication {
    
    public static void main(String[] args) {
//       UIConfig UIConfig = new UIConfig();
//       UIConfig.executeConfig();

        AutoUIConfigDyn autoUIConfig;
        if (args.length > 0 && args[0] != null && !args[0].trim().isEmpty()) {
            System.out.println("Config argument detected: " + args[0]);
            autoUIConfig = new AutoUIConfigDyn(args[0]); // Pass the JSON path
        } else {
            System.out.println("No arguments detected. Falling back to default profile.");
            autoUIConfig = new AutoUIConfigDyn();
        }
        autoUIConfig.executeConfig();

//        AutoUIConfig autoUIConfig = new AutoUIConfig();
//        autoUIConfig.executeConfig();
    }
}
