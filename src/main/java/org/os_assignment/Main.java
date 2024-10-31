package org.os_assignment;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

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

            if (Arrays.asList(command).contains(">")) {
                int index = Arrays.asList(command).indexOf(">");

                String[] cmdBeforeRedirect = Arrays.copyOfRange(command, 0, index);
                String outputFile = command[index + 1];

                CLI.redirectOutputByRewriting(cmdBeforeRedirect, outputFile,currentDir);
                continue;
            }else if(Arrays.asList(command).contains(">>")) {
                int index = Arrays.asList(command).indexOf(">>");

                String[] cmdBeforeRedirect = Arrays.copyOfRange(command, 0, index);
                String outputFile = command[index + 1];

                CLI.redirectOutputByAppending(cmdBeforeRedirect, outputFile,currentDir);
                continue;
            }else if(Arrays.asList(command).contains("|")){
                int index = Arrays.asList(command).indexOf("|");

                String[] cmdBeforeRedirect = Arrays.copyOfRange(command, 0, index);
                String[] cmdAfterRedirect = Arrays.copyOfRange(command, index+1, command.length-1);

                CLI.pipe(cmdBeforeRedirect, cmdAfterRedirect, currentDir);
                continue;
            }

            switch(command[0]){

                // help command - displays brief description for each command implemneted
                case "help":
                    Map<String,String> availableCommands = CLI.displayHelp();
                    System.out.println("availableCommands");
                    for (Map.Entry<String, String> entry : availableCommands.entrySet()) {
                        System.out.println(entry.getKey() + " - " + entry.getValue());
                    }
                    break;

                // exit command - exits from terminal using built-in function
                case "exit":
                    System.out.println("Exiting CLI..");
                    System.exit(0);
                    break;

                // pwd - calls pwd function
                case "pwd":
                    System.out.println(CLI.printWorkingDirectory(currentDir));
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

            case "touch":
                    if (command.length > 1) {
                        CLI.touch(command[1], userDir);
                    } else {
                        System.out.println("touch: Missing file name");
                    }
                    break;

                case "mv":
                    if (command.length == 3) {
                        // Call the mv function with the current directory, source, and destination paths
                        CLI.mv(currentDir, command[1], command[2]);
                    } else {
                        System.out.println("Invalid usage. Correct usage: mv <source> <destination>");
                    }
                    break;

                case "rm":
                    if (command.length == 2) {
                        CLI.rm(currentDir, command[1]);
                    } else {
                        System.out.println("Invalid usage. Correct usage: rm <file|directory>");
                    }
                    break;

                case "cat":
                    if (command.length == 2) {
                        CLI.cat(currentDir, command[1]);
                    } else {
                        System.out.println("Invalid usage. Usage: cat <file>");
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