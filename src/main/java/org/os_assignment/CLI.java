package org.os_assignment;
import java.io.File;

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
//            case "\\":
            // argument is a path
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

    //ls - list files in directory

    //ls -a

    //ls -r

    //mkdir

    //rmdir

    //touch

    //mv

    //rm

    //cat

    //>

    //>>

    //|

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
