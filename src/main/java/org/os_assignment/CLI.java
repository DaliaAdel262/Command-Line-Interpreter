package org.os_assignment;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class CLI {


    // pwd - printing working directory(current directory)
    public static void printWorkingDirectory(String currentDir){

        // print current directory using currentDir param passed from main
        System.out.println(currentDir);
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
//            case ".":
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
                    // if satisfy any condition, return invalid directory
                    return "Invalid directory";
                }
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
    public static void cat(String currentDir, String fileName) {
        try {
            // Resolve the file path based on the current directory
            Path filePath = Paths.get(currentDir, fileName).normalize();

            // Check if the file exists and is readable
            if (!Files.exists(filePath)) {
                System.out.println("cat: " + fileName + ": No such file or directory");
                return;
            }
            if (!Files.isRegularFile(filePath)) {
                System.out.println("cat: " + fileName + ": Not a regular file");
                return;
            }

            // Open and read the file line by line
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.out.println("cat: Error reading the file - " + e.getMessage());
        }
    }

    //>

    //>>

    //|-> pipe connects the output of one command to the input of another

    //exit

    //help
    public static void displayHelp(){
        System.out.println("Available commands:");
        System.out.println("cd [directory] - Change the current directory to the specified directory.");
        System.out.println("pwd - Print the current working directory.");
        System.out.println("ls - List files in the current directory.");
        System.out.println("ls -a - List all files, including hidden files in the current directory.");
        System.out.println("ls -r - List files in the current directory recursively.");
        System.out.println("mkdir [directory] - Create a new directory with the specified name.");
        System.out.println("rmdir [directory] - Remove the specified directory (must be empty).");
        System.out.println("touch [file] - Create a new empty file with the specified name or update the timestamp of an existing file.");
        System.out.println("mv [source] [destination] - Move or rename a file or directory from source to destination.");
        System.out.println("rm [file] - Remove the specified file.");
        System.out.println("cat [file] - Display the contents of the specified file.");
        System.out.println("> [command] - Redirect output to a file, overwriting the file if it exists.");
        System.out.println(">> [command] - Redirect output to a file, appending to the file if it exists.");
        System.out.println("| [command] - Pipe output from one command to another command.");
        System.out.println("exit - Exit the command-line interpreter.");
        System.out.println("help - Display this help information.");
    }
}
