import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.os_assignment.CLI;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;


public class CLITest {
    private CLI cli;

    // pwd test
    @Test
    public void testPwd() {
        cli = new CLI();
        String expectedDir  = System.getProperty("user.dir");

        String currentDir = cli.printWorkingDirectory(expectedDir);

        assertEquals(expectedDir, currentDir.trim(), "Current directory should match");
    }

    // cd test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//        cli.executeCommand("mkdir testDir"); // Create a test directory
//    }
//
//    @Test
//    public void testChangeToValidDirectory() {
//        cli.executeCommand("cd testDir");
//        assertEquals("testDir", cli.getCurrentDirectory().getName(), "Should change to valid directory");
//    }
//
//    @Test
//    public void testChangeToInvalidDirectory() {
//        String output = cli.executeCommand("cd invalidDir");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testChangeToParentDirectory() {
//        cli.executeCommand("cd testDir");
//        cli.executeCommand("cd ..");
//        assertEquals("..", cli.getCurrentDirectory().getName(), "Should change back to parent directory");
//    }

    // ls test
    @BeforeEach
    public void setUp() throws IOException {
        cli = new CLI();
        cli.executeCommand("mkdir testDir");
        cli.executeCommand("touch testDir/file1.txt");
        cli.executeCommand("touch testDir/file2.txt");
    }

    @Test
    public void testLs() {
        String output = cli.executeCommand("ls testDir");
        assertTrue(output.contains("file1.txt") && output.contains("file2.txt"), "Should list files in directory");
    }

    @Test
    public void testLsA() {
        String output = cli.executeCommand("ls -a testDir");
        assertTrue(output.contains("file1.txt") && output.contains("file2.txt") && output.contains("."), "Should list all files including hidden");
    }

    @Test
    public void testLsR() {
        String output = cli.executeCommand("ls -r testDir");
        assertTrue(output.contains("file1.txt") && output.contains("file2.txt"), "Should list files in reverse order");
    }

    @Test
    public void testLsInvalidDirectory() {
        String output = cli.executeCommand("ls invalidDir");
        assertTrue(output.contains("No such file or directory"), "Error message should be printed for invalid directory");
    }

    // mkdir test
    @BeforeEach
    public void setUp() {
        cli = new CLI();
    }

    @Test
    public void testMakeDirectory() {
        cli.executeCommand("mkdir testDir");
        assertTrue(new File("testDir").exists(), "Directory should be created");
    }

    @Test
    public void testMakeExistingDirectory() {
        cli.executeCommand("mkdir testDir");
        cli.executeCommand("mkdir testDir"); // Attempt to create it again
        assertTrue(new File("testDir").exists(), "Directory should still exist after trying to create it again");
    }

    @Test
    public void testMakeDirectoryWithNoName() {
        String output = cli.executeCommand("mkdir");
        assertTrue(output.contains("No name specified"), "Error message should be printed for no directory name");
    }

    //rmdir test
    @BeforeEach
    public void setUp() {
        cli = new CLI();
        cli.executeCommand("mkdir testDir"); // Create a test directory
    }

    @Test
    public void testRemoveExistingDirectory() {
        cli.executeCommand("rmdir testDir");
        assertFalse(new File("testDir").exists(), "Directory should be removed");
    }

    @Test
    public void testRemoveNonExistingDirectory() {
        String output = cli.executeCommand("rmdir nonExistingDir");
        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
    }

    @Test
    public void testRemoveDirectoryWithNoName() {
        String output = cli.executeCommand("rmdir");
        assertTrue(output.contains("No name specified"), "Error message should be printed for no directory name");
    }

    // touch test
    @BeforeEach
    public void setUp() {
        cli = new CLI();
    }

    @Test
    public void testCreateFile() {
        cli.executeCommand("touch testFile.txt");
        assertTrue(new File("testFile.txt").exists(), "File should be created");
    }

    @Test
    public void testCreateExistingFile() {
        cli.executeCommand("touch testFile.txt");
        cli.executeCommand("touch testFile.txt"); // Attempt to create it again
        assertTrue(new File("testFile.txt").exists(), "File should still exist after trying to create it again");
    }

    @Test
    public void testCreateFileWithNoName() {
        String output = cli.executeCommand("touch");
        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
    }

    // mv test
    private File sourceFile;
    private File destinationFile;

    @BeforeEach
    public void setUp() throws IOException {
        cli = new CLI();
        sourceFile = new File("source.txt");
        destinationFile = new File("destination.txt");
        sourceFile.createNewFile(); // Create a test file
    }

    @Test
    public void testMoveFile() {
        cli.executeCommand("mv source.txt destination.txt");
        assertFalse(sourceFile.exists(), "Source file should not exist after moving");
        assertTrue(destinationFile.exists(), "Destination file should exist after moving");
    }

    @Test
    public void testMoveNonExistingFile() {
        String output = cli.executeCommand("mv nonexisting.txt destination.txt");
        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
    }

    @Test
    public void testMoveFileWithNoDestination() {
        String output = cli.executeCommand("mv source.txt");
        assertTrue(output.contains("No destination specified"), "Error message should be printed");
    }

    // rm test
    private CLI cli;
    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        cli = new CLI();
        testFile = new File("testFile.txt");
        testFile.createNewFile(); // Create a test file for removal tests
    }

    @Test
    public void testRemoveExistingFile() {
        cli.executeCommand("rm testFile.txt");
        assertFalse(testFile.exists(), "File should be removed");
    }

    @Test
    public void testRemoveNonExistingFile() {
        String output = cli.executeCommand("rm nonExistingFile.txt");
        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
    }

    @Test
    public void testRemoveFileWithNoName() {
        String output = cli.executeCommand("rm");
        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
    }

    // cat test
    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        cli = new CLI();
        testFile = new File("testCatFile.txt");
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, world!");
        }
    }

    @Test
    public void testCatFile() {
        String output = cli.executeCommand("cat testCatFile.txt");
        assertEquals("Hello, world!", output.trim(), "Output should match file content");
    }

    @Test
    public void testCatNonExistingFile() {
        String output = cli.executeCommand("cat nonExistingFile.txt");
        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
    }

    @Test
    public void testCatFileWithNoName() {
        String output = cli.executeCommand("cat");
        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
    }


    // >, >> and | test
    @Test
    public void testPwdCommandWithAbsolutePath() throws IOException {
        // Setup
        String[] command = {"pwd"};
        String fileToStoreOutput = "C:\\Users\\DELL\\Desktop\\output.txt";
        String currentDir = "C:\\Users\\DELL";
        File outputFile = new File(fileToStoreOutput);
        outputFile.delete();

        // Execute
        CLI.redirectOutputByAppending(command, fileToStoreOutput, currentDir);

        // Verify
        assertTrue(outputFile.exists());
        String content = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(currentDir, content.trim());
    }

    @Test
    public void testHelpCommandWithRelativePath() throws IOException {
        cli = new CLI();
        // help >> output.txt
        // Setup
        String[] command = {"help"};
        String fileToStoreOutput = "output.txt";
        String currentDir = "C:\\Users\\DELL";
        File outputFile = new File(currentDir, fileToStoreOutput);
        outputFile.delete();  // Ensure the file doesn't exist before the test

        // Execute
        CLI.redirectOutputByAppending(command, fileToStoreOutput, currentDir);

        // Verify
        Map<String, String> availableCommands = cli.displayHelp();
        StringBuilder expectedOutput = new StringBuilder();
        for (Map.Entry<String, String> entry : availableCommands.entrySet()) {
            expectedOutput.append(entry.getKey()).append(" - ").append(entry.getValue()).append(System.lineSeparator());
        }

        String fileContent = new String(Files.readAllBytes(outputFile.toPath())).trim();
        // Remove the trailing newline for a consistent comparison
        String expectedContent = expectedOutput.toString().trim();

        // Compare expected output with the file content
        assertEquals(expectedContent, fileContent);
        assertFalse(fileContent.isEmpty()); // Help output should not be empty
    }

//    @Test
//    public void testNoOutputCommand() {
//        // Setup
//        String[] command = {"ls"};
//        String fileToStoreOutput = "output.txt";
//        String currentDir = "C:\\Users\\DELL";
//        File outputFile = new File(currentDir, fileToStoreOutput);
//
//        // Ensure no previous file exists
//        outputFile.delete();
//
//        // Execute
//        CLI.redirectOutputByAppending(command, fileToStoreOutput, currentDir);
//
//        // Verify
//        assertFalse(outputFile.exists());  // No file should be created
//    }

    @Test
    public void testPipeOperator() {
        // Assuming you have a valid implementation of the pipe command
        cli.executeCommand("echo Hello | grep Hello");
        // Here you would check that the output is as expected
        // This might require capturing console output or mocking
    }

    @Test
    public void testInvalidCommand() {
        // Setup
        String[] command = {"invalid"};
        String fileToStoreOutput = "output.txt";
        String currentDir = "C:\\Users\\DELL";
        File outputFile = new File(currentDir, fileToStoreOutput);

        // Ensure no previous file exists
        outputFile.delete();

        // Execute
        CLI.redirectOutputByAppending(command, fileToStoreOutput, currentDir);

        // Verify
        assertFalse(outputFile.exists());  // No file should be created
    }

}
