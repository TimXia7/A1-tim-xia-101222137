import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {

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

    // The following are tests for username and password validation (resp 4)
    // Usernames can be any String
    // Passwords have to have at least 1 number, 1 uppercase letter, and 1 lower case letter
    @Test
    @DisplayName("Default pass scenario, username and password are valid and match a existing borrower")
    void RESP_04_test_01() {
        Library library = new Library();
        assertEquals(0, library.validate("Bob_White", "Password123"));
    }

    @Test
    @DisplayName("Fail scenario: password does not have a number")
    void RESP_04_test_02() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "Password"));
    }

    @Test
    @DisplayName("Fail scenario: password does not have an uppercase letter")
    void RESP_04_test_03() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "password123"));
    }

    @Test
    @DisplayName("Fail scenario: password does not have a lowercase letter")
    void RESP_04_test_04() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "PASSWORD123"));
    }

    @Test
    @DisplayName("Fail scenario: password is only numbers")
    void RESP_04_test_05() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "123123123"));
    }

    @Test
    @DisplayName("Fail scenario: password is only lower case letters")
    void RESP_04_test_06() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "password"));
    }

    @Test
    @DisplayName("Fail scenario: password is only upper case letters")
    void RESP_04_test_07() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "PASSWORD"));
    }

    @Test
    @DisplayName("Fail scenario: password is not at least 8 characters long")
    void RESP_04_test_08() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", "Pass123"));
    }

    @Test
    @DisplayName("Fail scenario: Empty password")
    void RESP_04_test_09() {
        Library library = new Library();
        assertEquals(1, library.validate("Bob_White", ""));
    }

    @Test
    @DisplayName("Fail scenario: Valid entry, does not match with DB")
    void RESP_04_test_10() {
        Library library = new Library();
        assertEquals(2, library.validate("Bob_White_Cousin", "PassworD321"));
    }




}
