import main.*;
import main.SecurityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    // The following are tests for username and password validation (resp 4)
    // Usernames can be any String
    // Passwords have to have at least 1 number, 1 uppercase letter, and 1 lower case letter
    @Test
    @DisplayName("Default pass scenario, username and password are valid and match a existing borrower")
    void RESP_04_test_01() {
        SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(0, securityManager.validate("Bob_White", "Password123"));
    }

    @Test
    @DisplayName("Fail scenario: password does not have a number")
    void RESP_04_test_02() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "Password"));
    }

    @Test
    @DisplayName("Fail scenario: password does not have an uppercase letter")
    void RESP_04_test_03() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "password123"));
    }

    @Test
    @DisplayName("Fail scenario: password does not have a lowercase letter")
    void RESP_04_test_04() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "PASSWORD123"));
    }

    @Test
    @DisplayName("Fail scenario: password is only numbers")
    void RESP_04_test_05() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "123123123"));
    }

    @Test
    @DisplayName("Fail scenario: password is only lower case letters")
    void RESP_04_test_06() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "password"));
    }

    @Test
    @DisplayName("Fail scenario: password is only upper case letters")
    void RESP_04_test_07() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "PASSWORD"));
    }

    @Test
    @DisplayName("Fail scenario: password is not at least 8 characters long")
    void RESP_04_test_08() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", "Pass123"));
    }

    @Test
    @DisplayName("Fail/Boundary scenario: Empty password")
    void RESP_04_test_09() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(1, securityManager.validate("Bob_White", ""));
    }

    @Test
    @DisplayName("Fail scenario: Valid entry, does not match with DB")
    void RESP_04_test_10() {
        main.SecurityManager securityManager = new main.SecurityManager(new Library(), "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        assertEquals(2, securityManager.validate("Bob_White_Cousin", "PassworD321"));
    }

    @Test
    @DisplayName("Successful login should return some token")
    void RESP_05_test_01() {
        Library library = new Library();
        String token = library.login("Bob_White", "Password123");

        assertTrue(token != null && !token.isEmpty(), "Token should be a non-empty string");
    }

    @Test
    @DisplayName("Failed login should return null")
    void RESP_05_test_02() {
        Library library = new Library();
        String token = library.login("Bob_White99", "Password123");

        // Assert that login failed
        assertNull(token, "Token should be null for failed login");
    }


    @Test
    @DisplayName("Successful login assigns a session token to the borrower")
    void RESP_05_test_03() {
        Library library = new Library();
        String token = library.login("Bob_White", "Password123");

        // Retrieve the borrower and check their session token
        Borrower borrower = library.getBorrowerByName("Bob_White");
        assertTrue(
                borrower.getSessionToken() != null && borrower.getSessionToken().equals(token),
                "main.Borrower should have a session token after login, and it should match the returned token"
        );

    }

    @Test
    @DisplayName("Successful login assigns a session token to the borrower")
    void RESP_025_test_01() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Bob_White\nPassword123\n0\n1\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.run();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Press 1 to Confirm the Logout"));
    }

    @Test
    @DisplayName("Successful login assigns a session token to the borrower")
    void RESP_025_test_02() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Bob_White\nPassword123\n0\n1\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.run();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Press 2 to return to the Main Menu"));
    }

    @Test
    @DisplayName("After returning to main menu, logout anyways")
    void RESP_025_test_03() {
        // capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        ByteArrayInputStream inContent = new ByteArrayInputStream("Bob_White\nPassword123\n0\n2\n0\n1\n".getBytes());
        System.setIn(inContent);

        // init the library and UI
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        ui.run();

        System.setOut(originalOut);
        System.setIn(System.in);

        // Check that the password prompt was printed
        String output = outContent.toString();
        assertTrue(output.contains("Press 2 to return to the Main Menu"));
    }
}
