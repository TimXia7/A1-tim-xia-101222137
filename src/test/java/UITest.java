import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UITest {

    @Test
    @DisplayName("The system should prompt the user for their username")
    void RESP_03_test_01() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("user\npass\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.promptCredentials();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the exact username prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Enter username:"), "Should prompt for password");
    }

    @Test
    @DisplayName("The system should prompt the user for their password")
    void RESP_03_test_02() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("user\npass\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.promptCredentials();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Enter password:"), "Should prompt for password");
    }

    @Test
    @DisplayName("Username prompt should appear before password prompt")
    void RESP_03_test_03() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("user\npass\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.promptCredentials();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check order of prompts
        String output = outContent.toString();
        int usernameIndex = output.indexOf("Enter username:");
        int passwordIndex = output.indexOf("Enter password:");

        // Single assertion
        assertTrue(
                usernameIndex >= 0 && passwordIndex >= 0 && usernameIndex < passwordIndex,
                "Username prompt should appear before password prompt, and both prompts should exist"
        );
    }

    @Test
    @DisplayName("After login, available operations should be presented: borrow a book")
    void RESP_07_test_01() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Bob_White\nPassword123\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.run();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Borrow a Book"));
    }

    @Test
    @DisplayName("After login, available operations should be presented: return a book")
    void RESP_07_test_02() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Bob_White\nPassword123\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.run();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Return a book"));
    }

    @Test
    @DisplayName("After login, available operations should be presented: logout")
    void RESP_07_test_03() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Bob_White\nPassword123\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.run();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Logout"));
    }


}
