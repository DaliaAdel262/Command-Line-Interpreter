package org.os_assignment;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Initializing scanner for user input and creating instance of CLI
        Scanner scanner = new Scanner(System.in);
        CLI cli = new CLI();

        // Accessing user's home directory as usually done in cmds
        String currentDir = System.getProperty("user.home");
        File userDir = new File(currentDir);

        while(true){
            System.out.print(currentDir + '>');

            // Splitting command into substrings (command and any arguments) and storing them in array
            String[] command = scanner.nextLine().split("\\s+");

            //command = [ls,-a]

            switch(command[0]){

                // help command - displays brief description for each command implemneted
                case "help":
                    CLI.displayHelp();
                    break;

                // exit command - exits from terminal using built-in function
                case "exit":
                    System.out.println("Exiting CLI..");
                    System.exit(0);
                    break;

                // pwd - calls pwd function
                case "pwd":
                    CLI.printWorkingDirectory(currentDir);
                    break;

                // cd - calls cd function and displays output depending on return from function
                case "cd":
                    if(command.length>1){
                        String parentDir = CLI.changeCurrentDirectory(command[1], userDir);

                        if(parentDir == "Invalid directory"){
                            System.out.println("Invalid directory");
                        }else if(parentDir!=""){
                            // if a non-empty string was returned, then update currentDir to string returned
                            currentDir = parentDir;
                            userDir = new File(currentDir);
                        };
                    }

                    break;

                // default for any invalid command
                default:
                    System.out.println(String.join(" ",command) + " is not recognized as an internal or external command,\n" +
                            "operable program or batch file.");
                    break;
            }
        }

    }
}