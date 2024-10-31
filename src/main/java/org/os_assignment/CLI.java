package org.os_assignment;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class CLI {

    // pwd - printing working directory(current directory)
    public static String printWorkingDirectory(String currentDir){

        // return current directory using currentDir param passed from main
        return currentDir;

    }

    //cd - change directory
    public static String changeCurrentDirectory(String arg, File currentPath){
        switch(arg){

            // Moving up one directory, moving up to parent directory of current directory
            case "..":

                // Getting parent directory using file
                File parentDir = currentPath.getParentFile();

                if(parentDir != null){
                    // return string of the file parentDir
                    return parentDir.getPath();
                }else{
                    // if current directory is already parent directory, do nothing
                    return "";
                }

            case "\\":
                return currentPath.toPath().getRoot().toString();

            default:
                File newDir;
                if (new File(arg).isAbsolute()) {
                    // checking if path is absolute, set arg as new dir
                    newDir = new File(arg);
                } else {
                    // combine arg with current path and storing in new dir
                    newDir = new File(currentPath, arg);
                }

                if (newDir.exists() && newDir.isDirectory()) {
                    // if new dir exists and is a directory, return as a string
                    return newDir.getPath();
                } else {
                    // if doesn't satisfy any condition, return invalid directory
                    return "Invalid directory";
                }
        }
    }


    // ls && ls -r
    public static File[] listFiles(File currentPath) {
        File[] files;
        files = currentPath.listFiles(f -> !f.isHidden()); // List all files, including hidden
        return files;
    }

    //ls -a
    public static File[] listAllFiles(File currentPath) {
        File[] files;
        files = currentPath.listFiles(); // List all files, including hidden
        return files;
    }

    // List files recursively and return an array of all files
    public static File[] listFilesRecursively(File directory) {
        List<File> fileList = new ArrayList<>();
        accumulateFiles(directory, fileList);
        return fileList.toArray(new File[0]);
    }

    // Helper method for recursion
    private static void accumulateFiles(File directory, List<File> fileList) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                fileList.add(file);  // Add the file/directory to the list
                if (file.isDirectory()) {
                    accumulateFiles(file, fileList); // Recursively add files from subdirectory
                }
            }
        }
    }

    // mkdir - create a new directory
    public static Boolean createDirectory(File currentPath , String directoryName)  {
        File newDir = new File(currentPath, directoryName);
        if (newDir.mkdir()) {
            System.out.println("Directory created: " + newDir.getPath());
            return true;
        } else {
            System.out.println("Failed to create directory: " + directoryName);
            return false;
        }
    }


    //rmdir - remove directory
    public static void removeDirectory(File currentPath , String directoryName) {
        File dirToDelete = new File(currentPath, directoryName);

        if (dirToDelete.exists() && dirToDelete.isDirectory()) {
            if (dirToDelete.delete()) {
                System.out.println("Directory deleted: " + directoryName);
            } else {
                System.out.println("Failed to delete directory: " + directoryName);
            }
        } else {
            System.out.println("Directory does not exist: " + directoryName);
        }
    }


    //touch-> creates new file or changes the timestamp
    public static void touch(String filename, File currentDir) {
        File file = new File(currentDir, filename);  // Resolve file in current directory

        try {
            if (file.exists()) {
                // If the file exists, update the last modified timestamp
                boolean success = file.setLastModified(System.currentTimeMillis());
                if (success) {
                    System.out.println("Timestamp updated for: " + filename);
                } else {
                    System.out.println("Failed to update timestamp for: " + filename);
                }
            } else {
                // Create a new file if it doesn't exist
                boolean success = file.createNewFile();
                if (success) {
                    System.out.println("File created: " + filename);
                } else {
                    System.out.println("Failed to create file: " + filename);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    //mv-> move or rename files
    public static void mv(String currentDir, String sourcePath, String destinationPath) {
        // Resolve source and destination paths relative to the current directory
        Path source = Paths.get(currentDir).resolve(sourcePath).normalize();
        Path destination = Paths.get(currentDir).resolve(destinationPath).normalize();

        // Check if the source file/directory exists
        if (!Files.exists(source)) {
            System.out.println("mv: cannot stat '" + source + "': No such file or directory");
            return;
        }

        try {
            // Perform the move/rename operation
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved '" + source + "' to '" + destination + "'");
        } catch (IOException e) {
            System.out.println("mv: failed to move '" + source + "' to '" + destination + "': " + e.getMessage());
        }
    }

    //rmdir

    //rm-> remove file or directory
    public static void rm(String currentDir, String targetPath) {
        // Resolve the target path relative to the current directory
        Path target = Paths.get(currentDir).resolve(targetPath).normalize();

        // Check if the target exists
        if (!Files.exists(target)) {
            System.out.println("rm: cannot remove '" + targetPath + "': No such file or directory");
            return;
        }

        try {
            // Check if it's a directory and delete it recursively if necessary
            if (Files.isDirectory(target)) {
                deleteDirectoryRecursively(target.toFile());
            } else {
                Files.delete(target); // For regular files
                System.out.println("Removed: " + target);
            }
        } catch (IOException e) {
            System.out.println("rm: failed to remove '" + target + "': " + e.getMessage());
        }
    }

    // Helper method to delete a directory recursively
    private static void deleteDirectoryRecursively(File directory) throws IOException {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectoryRecursively(file); // Recursively delete contents
            }
        }
        if (directory.delete()) {
            System.out.println("Removed directory: " + directory.getPath());
        } else {
            throw new IOException("Failed to delete directory: " + directory.getPath());
        }
    }

    //cat-> read and print the file content
    public static String cat(String currentDir, String fileName) {
        try {
            // Resolve the file path based on the current directory
            Path filePath = Paths.get(currentDir, fileName).normalize();

            // Check if the file exists and is readable
            if (!Files.exists(filePath)) {
                System.out.println("cat: " + fileName + ": No such file or directory");
                return ("cat: " + fileName + ": No such file or directory");
            }
            if (!Files.isRegularFile(filePath)) {
                return ("cat: " + fileName + ": Not a regular file");
            }

            // Open and read the file line by line
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    return line;
                }
            }

        } catch (IOException e) {
            return ("cat: Error reading the file - " + e.getMessage());
        }
        return null;
    }

    //>
    public static void redirectOutputByRewriting(String[] command,String fileToStoreOutput,String currentDir){
        String output;
        String err = "";
        switch(command[0]){
            case "pwd":
                if(command.length>1){
                    err = "This command is not supported by the pwd utility.";
                }
                // must return string
                output = CLI.printWorkingDirectory(currentDir);
                break;
            case "help":
                if(command.length>1){
                    err = "This command is not supported by the help utility.";
                }
                // help must return string
                Map<String, String> helpCommands = CLI.displayHelp();
                output = helpCommands.toString();
                break;
            case "ls":
            case "touch":
            case "cat":
            default:
                // a command with no output, do nothing
                return;
        }

        File file;
        if (new File(fileToStoreOutput).isAbsolute()) {
            file = new File(fileToStoreOutput);
        } else {
            file = new File(currentDir, fileToStoreOutput);
        }

        if(!err.isEmpty()){
            try (FileWriter fw = new FileWriter(file, false);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(!output.isEmpty()){

            try (FileWriter fw = new FileWriter(file, false);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //>>
    public static void redirectOutputByAppending(String[] command,String fileToStoreOutput,String currentDir){
        String output;
        String err = "";
        switch(command[0]){
            case "pwd":
                if(command.length>1){
                    err = "This command is not supported by the pwd utility.";
                }
                // must return string
                output = CLI.printWorkingDirectory(currentDir);
                break;
            case "help":
                if(command.length>1){
                    err = "This command is not supported by the help utility.";
                }
                // help must return string
                Map<String, String> helpCommands = CLI.displayHelp();
                output = helpCommands.toString();
                break;
            case "ls":
            case "touch":
            case "cat":
            default:
                // a command with no output, do nothing
                return;
        }

        File file;
        if (new File(fileToStoreOutput).isAbsolute()) {
            file = new File(fileToStoreOutput);
        } else {
            file = new File(currentDir, fileToStoreOutput);
        }

    //|-> pipe connects the output of one command to the input of another
        if(!err.isEmpty()){
            try (FileWriter fw = new FileWriter(file, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(!output.isEmpty()){

            try (FileWriter fw = new FileWriter(file, true);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.println(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //|
    public static void pipe(String[] command1, String[] command2, String currentDir){
        String[] output = {};
        String err = "";
        switch(command1[0]){
            case "ls":
            case "ls -a":
            case "ls -r":
                System.out.println(":)");
                break;
            default:
                System.out.println("Command not compatible with pipe: " + command1[0]);
                return;
        }
        
        if(output.length!=0 || output != null){
            switch(command2[0]){

                // cd - calls cd function and displays output depending on return from function
                case "cat":
                    for(int i=0;i<output.length;i++){
                        CLI.cat(currentDir,output[i]);
                    }
                    break;

                // default for any invalid command
                default:
                    System.out.println("Command not compatible with pipe: " + command2[0]);
                    return;
            }
        }else{
            System.out.println("No output to pipe from command: " + command1[0]);
            return;
        }
    }

    //exit

    //help
    public static Map<String,String> displayHelp(){
        Map<String, String> helpCommands = new HashMap<>();

        helpCommands.put("cd [directory]", "Change the current directory to the specified directory.");
        helpCommands.put("pwd", "Print the current working directory.");
        helpCommands.put("ls", "List files in the current directory.");
        helpCommands.put("ls -a", "List all files, including hidden files in the current directory.");
        helpCommands.put("ls -r", "List files in the current directory recursively.");
        helpCommands.put("mkdir [directory]", "Create a new directory with the specified name.");
        helpCommands.put("rmdir [directory]", "Remove the specified directory (must be empty).");
        helpCommands.put("touch [file]", "Create a new empty file with the specified name or update the timestamp of an existing file.");
        helpCommands.put("mv [source] [destination]", "Move or rename a file or directory from source to destination.");
        helpCommands.put("rm [file]", "Remove the specified file.");
        helpCommands.put("cat [file]", "Display the contents of the specified file.");
        helpCommands.put("> [command]", "Redirect output to a file, overwriting the file if it exists.");
        helpCommands.put(">> [command]", "Redirect output to a file, appending to the file if it exists.");
        helpCommands.put("| [command]", "Pipe output from one command to another command.");
        helpCommands.put("exit", "Exit the command-line interpreter.");
        helpCommands.put("help", "Display this help information.");

        return helpCommands;
    }
}
