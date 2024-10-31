import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.os_assignment.CLI;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.Map;


public class CLITest {
    private CLI cli;

    // pwd test
    @Test
    public void testPwd() {
        cli = new CLI();
        String expectedDir  = System.getProperty("user.dir");

        String currentDir = cli.printWorkingDirectory(expectedDir);

        assertEquals(expectedDir, currentDir, "Current directory should match");
    }

    // cd test

    @Test
    public void testChangeToDesktopDirectory() {
        cli = new CLI();

        File currentDir = new File("C:\\Users\\DELL");
        String expectedOutput ="C:\\Users\\DELL\\Desktop";
        String output = cli.changeCurrentDirectory("desktop",currentDir);
        assertEquals(expectedOutput,output);
    }

    @Test
    public void testChangeToInvalidDirectory() {
        cli = new CLI();

        File currentDir = new File("C:\\Users\\DELL\\Desktop");
        String output = cli.changeCurrentDirectory("mee",currentDir);
        assertEquals("Invalid directory",output);
    }

    @Test
    public void testChangeToParentDirectory() {
        cli = new CLI();

        File currentDir = new File("C:\\Users\\DELL");
        String output = cli.changeCurrentDirectory("\\",currentDir);
        assertEquals("C:\\",output);
    }

    @Test
    public void testChangeToPreviousDirectory() {
        cli = new CLI();

        File currentDir = new File("C:\\Users\\DELL");
        String expectedOutput ="C:\\Users";
        String output = cli.changeCurrentDirectory("..",currentDir);
        assertEquals(expectedOutput,output);
    }

    // ls test
//    @BeforeEach
//    public void setUp() throws IOException {
//        cli = new CLI();
//        cli.executeCommand("mkdir testDir");
//        cli.executeCommand("touch testDir/file1.txt");
//        cli.executeCommand("touch testDir/file2.txt");
//    }
//
//    @Test
//    public void testLs() {
//        String output = cli.executeCommand("ls testDir");
//        assertTrue(output.contains("file1.txt") && output.contains("file2.txt"), "Should list files in directory");
//    }
//
//    @Test
//    public void testLsA() {
//        String output = cli.executeCommand("ls -a testDir");
//        assertTrue(output.contains("file1.txt") && output.contains("file2.txt") && output.contains("."), "Should list all files including hidden");
//    }
//
//    @Test
//    public void testLsR() {
//        String output = cli.executeCommand("ls -r testDir");
//        assertTrue(output.contains("file1.txt") && output.contains("file2.txt"), "Should list files in reverse order");
//    }
//
//    @Test
//    public void testLsInvalidDirectory() {
//        String output = cli.executeCommand("ls invalidDir");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed for invalid directory");
//    }

    // mkdir test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//    }
//
//    @Test
//    public void testMakeDirectory() {
//        cli.executeCommand("mkdir testDir");
//        assertTrue(new File("testDir").exists(), "Directory should be created");
//    }
//
//    @Test
//    public void testMakeExistingDirectory() {
//        cli.executeCommand("mkdir testDir");
//        cli.executeCommand("mkdir testDir"); // Attempt to create it again
//        assertTrue(new File("testDir").exists(), "Directory should still exist after trying to create it again");
//    }
//
//    @Test
//    public void testMakeDirectoryWithNoName() {
//        String output = cli.executeCommand("mkdir");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no directory name");
//    }

    //rmdir test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//        cli.executeCommand("mkdir testDir"); // Create a test directory
//    }
//
//    @Test
//    public void testRemoveExistingDirectory() {
//        cli.executeCommand("rmdir testDir");
//        assertFalse(new File("testDir").exists(), "Directory should be removed");
//    }
//
//    @Test
//    public void testRemoveNonExistingDirectory() {
//        String output = cli.executeCommand("rmdir nonExistingDir");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testRemoveDirectoryWithNoName() {
//        String output = cli.executeCommand("rmdir");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no directory name");
//    }
//
//    // touch test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//    }
//
//    @Test
//    public void testCreateFile() {
//        cli.executeCommand("touch testFile.txt");
//        assertTrue(new File("testFile.txt").exists(), "File should be created");
//    }
//
//    @Test
//    public void testCreateExistingFile() {
//        cli.executeCommand("touch testFile.txt");
//        cli.executeCommand("touch testFile.txt"); // Attempt to create it again
//        assertTrue(new File("testFile.txt").exists(), "File should still exist after trying to create it again");
//    }
//
//    @Test
//    public void testCreateFileWithNoName() {
//        String output = cli.executeCommand("touch");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
//    }

    // mv test
//
//    @Test
//    public void testMoveNonExistingFile() {
//        String output = cli.executeCommand("mv nonexisting.txt destination.txt");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testMoveFileWithNoDestination() {
//        String output = cli.executeCommand("mv source.txt");
//        assertTrue(output.contains("No destination specified"), "Error message should be printed");
//    }

    // rm test

    @Test
    public void testRemoveExistingFile() {
        cli = new CLI();
        String currentDir = "C:\\Users\\DELL\\Desktop\\testDir";
        File fileToRemove = new File(currentDir, "rmtest.txt");
        cli.rm(currentDir,"rmtest.txt");
        assertFalse(fileToRemove.exists());
    }

    @Test
    public void testRemoveNonExistingFile() {
        CLI cli = new CLI();
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor)); // Redirect System.out

        String currentDir = "C:\\Users\\DELL\\Desktop\\testDir";
        cli.rm(currentDir, "idontexist.txt");

        String expectedOutput = "rm: cannot remove 'idontexist.txt': No such file or directory";
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        System.setOut(System.out);
    }

    // cat test
//    private File testFile;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        cli = new CLI();
//        testFile = new File("testCatFile.txt");
//        try (FileWriter writer = new FileWriter(testFile)) {
//            writer.write("Hello, world!");
//        }
//    }
//
//    @Test
//    public void testCatFile() {
//        String output = cli.executeCommand("cat testCatFile.txt");
//        assertEquals("Hello, world!", output.trim(), "Output should match file content");
//    }
//
//    @Test
//    public void testCatNonExistingFile() {
//        String output = cli.executeCommand("cat nonExistingFile.txt");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testCatFileWithNoName() {
//        String output = cli.executeCommand("cat");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
//    }


    // >, >> and | test
    @Test
    public void testRedirectionPwdCommandWithAbsolutePath() throws IOException {
        cli = new CLI();
        // test: pwd > C:\User\DELL\Desktop\output.txt

        String[] command = {"pwd"};
        String fileToStoreOutput = "C:\\Users\\DELL\\Desktop\\output.txt";
        String currentDir = "C:\\Users\\DELL";
        String commandOutput = cli.printWorkingDirectory(currentDir);
        File outputFile = new File(fileToStoreOutput);
        outputFile.delete();


        CLI.redirectOutput(command, fileToStoreOutput, currentDir, ">");


        assertTrue(outputFile.exists());
        String contentOfFile = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(commandOutput, contentOfFile.trim());
    }

    @Test
    public void testAppendRedirectionCatCommandWithRelativePath() throws IOException {
        cli = new CLI();
        // test: cat cattest.txt >> output.txt

        String[] command = {"cat","cattest.txt"};
        String fileToStoreOutput = "output.txt";
        String currentDir = "C:\\Users\\DELL";
        File outputFile = new File(currentDir, fileToStoreOutput);

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        CLI.redirectOutput(command, fileToStoreOutput, currentDir, ">>");

        String commandOutput = cli.cat(currentDir,"cattest.txt");
        String fileContent = new String(Files.readAllBytes(outputFile.toPath())).trim();

        assertTrue(outputFile.exists());
        assertTrue(fileContent.contains(commandOutput));
    }

    // ls test
    @BeforeEach
    public void setUp() throws IOException {
        cli = new CLI();
//        cli.executeCommand("mkdir testDir");
//        cli.executeCommand("touch testDir/file1.txt");
//        cli.executeCommand("touch testDir/file2.txt");
    }

    @Test
    public void testLs() {
        File expected = new File("C://Users//anas//ananas//a");
        File dir = new File("C://Users//anas//ananas");
        File[] output = cli.listFiles(dir);
        assertEquals(expected, output[0]);
    }

    @Test
    public void testLsA() {
        File[] expected = {
                new File("C://Users//anas//ananas//a"),
                new File("C://Users//anas//ananas//hidden"),
        };
        File dir = new File("C://Users//anas//ananas");
        File[] output = cli.listAllFiles(dir);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], output[i]);
        }

    }

//    @Test
//    public void testLsR() {
//        String output = cli.listFilesRecursively();
//        assertTrue(output.contains("file1.txt") && output.contains("file2.txt"), "Should list files in reverse order");
//    }
//
//    @Test
//    public void testLsInvalidDirectory() {
//        String output = cli.executeCommand("ls invalidDir");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed for invalid directory");
//    }
//
    // mkdir test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//    }
//
    @Test
    public void testMakeDirectory() {
        Boolean created = cli.createDirectory(new File("C://Users//anas//ananas"), "m");
        assertTrue(created);
    }
//
    @Test
    public void testMakeExistingDirectory() {
        cli.createDirectory(new File("C://Users//anas//ananas"), "new");
        Boolean didNotCreated = cli.createDirectory(new File("C://Users//anas//ananas"), "new"); // Attempt to create it again
        assertFalse(didNotCreated);
    }

//    @Test
//    public void testMakeDirectoryWithNoName() {
//        String output = cli.createDirectory(new File("C://Users//anas//ananas"));
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no directory name");
//    }
//
//    //rmdir test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//        cli.executeCommand("mkdir testDir"); // Create a test directory
//    }
//
//    @Test
//    public void testRemoveExistingDirectory() {
//        cli.executeCommand("rmdir testDir");
//        assertFalse(new File("testDir").exists(), "Directory should be removed");
//    }
//
//    @Test
//    public void testRemoveNonExistingDirectory() {
//        String output = cli.executeCommand("rmdir nonExistingDir");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testRemoveDirectoryWithNoName() {
//        String output = cli.executeCommand("rmdir");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no directory name");
//    }
//
//    // touch test
//    @BeforeEach
//    public void setUp() {
//        cli = new CLI();
//    }
//
//    @Test
//    public void testCreateFile() {
//        cli.executeCommand("touch testFile.txt");
//        assertTrue(new File("testFile.txt").exists(), "File should be created");
//    }
//
//    @Test
//    public void testCreateExistingFile() {
//        cli.executeCommand("touch testFile.txt");
//        cli.executeCommand("touch testFile.txt"); // Attempt to create it again
//        assertTrue(new File("testFile.txt").exists(), "File should still exist after trying to create it again");
//    }
//
//    @Test
//    public void testCreateFileWithNoName() {
//        String output = cli.executeCommand("touch");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
//    }
//
//    // mv test
//    private File sourceFile;
//    private File destinationFile;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        cli = new CLI();
//        sourceFile = new File("source.txt");
//        destinationFile = new File("destination.txt");
//        sourceFile.createNewFile(); // Create a test file
//    }
//
//    @Test
//    public void testMoveFile() {
//        cli.executeCommand("mv source.txt destination.txt");
//        assertFalse(sourceFile.exists(), "Source file should not exist after moving");
//        assertTrue(destinationFile.exists(), "Destination file should exist after moving");
//    }
//
//    @Test
//    public void testMoveNonExistingFile() {
//        String output = cli.executeCommand("mv nonexisting.txt destination.txt");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testMoveFileWithNoDestination() {
//        String output = cli.executeCommand("mv source.txt");
//        assertTrue(output.contains("No destination specified"), "Error message should be printed");
//    }
//
//    // rm test
//    private CLI cli;
//    private File testFile;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        cli = new CLI();
//        testFile = new File("testFile.txt");
//        testFile.createNewFile(); // Create a test file for removal tests
//    }
//
//    @Test
//    public void testRemoveExistingFile() {
//        cli.executeCommand("rm testFile.txt");
//        assertFalse(testFile.exists(), "File should be removed");
//    }
//
//    @Test
//    public void testRemoveNonExistingFile() {
//        String output = cli.executeCommand("rm nonExistingFile.txt");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testRemoveFileWithNoName() {
//        String output = cli.executeCommand("rm");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
//    }
//
//    // cat test
//    private File testFile;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        cli = new CLI();
//        testFile = new File("testCatFile.txt");
//        try (FileWriter writer = new FileWriter(testFile)) {
//            writer.write("Hello, world!");
//        }
//    }
//
//    @Test
//    public void testCatFile() {
//        String output = cli.executeCommand("cat testCatFile.txt");
//        assertEquals("Hello, world!", output.trim(), "Output should match file content");
//    }
//
//    @Test
//    public void testCatNonExistingFile() {
//        String output = cli.executeCommand("cat nonExistingFile.txt");
//        assertTrue(output.contains("No such file or directory"), "Error message should be printed");
//    }
//
//    @Test
//    public void testCatFileWithNoName() {
//        String output = cli.executeCommand("cat");
//        assertTrue(output.contains("No name specified"), "Error message should be printed for no file name");
//    }
//
//
//    // >, >> and | test
//    @Test
//    public void testPwdCommandWithAbsolutePath() throws IOException {
//        // Setup
//        String[] command = {"pwd"};
//        String fileToStoreOutput = "C:\\Users\\DELL\\Desktop\\output.txt";
//        String currentDir = "C:\\Users\\DELL";
//        File outputFile = new File(fileToStoreOutput);
//        outputFile.delete();
//
//        // Execute
//        CLI.redirectOutputByAppending(command, fileToStoreOutput, currentDir);
//
//        // Verify
//        assertTrue(outputFile.exists());
//        String content = new String(Files.readAllBytes(outputFile.toPath()));
//        assertEquals(currentDir, content.trim());
//    }
//
//    @Test
//    public void testHelpCommandWithRelativePath() throws IOException {
//        cli = new CLI();
//        // help >> output.txt
//        // Setup
//        String[] command = {"help"};
//        String fileToStoreOutput = "output.txt";
//        String currentDir = "C:\\Users\\DELL";
//        File outputFile = new File(currentDir, fileToStoreOutput);
//        outputFile.delete();  // Ensure the file doesn't exist before the test
//
//        // Execute
//        CLI.redirectOutputByAppending(command, fileToStoreOutput, currentDir);
//
//        // Verify
//        Map<String, String> availableCommands = cli.displayHelp();
//        StringBuilder expectedOutput = new StringBuilder();
//        for (Map.Entry<String, String> entry : availableCommands.entrySet()) {
//            expectedOutput.append(entry.getKey()).append(" - ").append(entry.getValue()).append(System.lineSeparator());
//        }
//
//        String fileContent = new String(Files.readAllBytes(outputFile.toPath())).trim();
//        // Remove the trailing newline for a consistent comparison
//        String expectedContent = expectedOutput.toString().trim();
//
//        // Compare expected output with the file content
//        assertEquals(expectedContent, fileContent);
//        assertFalse(fileContent.isEmpty()); // Help output should not be empty
//    }

    @Test
    public void testNoOutputCommand() throws IOException {

        String[] command = {"touch"};
        String fileToStoreOutput = "output.txt";
        String currentDir = "C:\\Users\\DELL";
        File outputFile = new File(currentDir, fileToStoreOutput);

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        String oldFileContent = new String(Files.readAllBytes(outputFile.toPath())).trim();

        CLI.redirectOutput(command, fileToStoreOutput, currentDir, ">>");
        String newFileContent = new String(Files.readAllBytes(outputFile.toPath())).trim();

        assertEquals(oldFileContent,newFileContent);
    }

    @Test
    public void testPipeOperator() {
        CLI cli = new CLI();

        String[] command1 = {"sort", "test1.txt"};
        String[] command2 = {"uniq"};
        String currentDir = "C:\\Users\\DELL\\Desktop\\testforos";

        List<String> output = cli.pipe(command1, command2, currentDir);
        List<String> expectedOutput = List.of("a", "b", "c", "d");

        assertEquals(expectedOutput, output);
    }

    @Test
    public void testInvalidCommand() {
        String[] command = {"invalid"};
        String fileToStoreOutput = "output.txt";
        String currentDir = "C:\\Users\\DELL";
        File outputFile = new File(currentDir, fileToStoreOutput);

        outputFile.delete();
        CLI.redirectOutput(command, fileToStoreOutput, currentDir, ">>");

        assertFalse(outputFile.exists());
    }

}
