
import main.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {

    @Test
    @DisplayName("main.Library catalogue size should be 20 upon initialization.")
    void RESP_01_test_01(){
        Library library = new Library();

        int size = library.getCatalogueSize();
        assertEquals(20, size);
    }

    @Test
    @DisplayName("main.Library should have a specific book after initialization - The Great Gatsby.")
    void RESP_01_test_02(){
        Library library = new Library();

        Book targetBook = library.getBookByTitle("The Great Gatsby");
        String targetBookTitle = targetBook.getTitle();
        assertEquals("The Great Gatsby", targetBookTitle);
    }

    @Test
    @DisplayName("Ensure that all books in the catalogue have valid data.")
    void RESP_01_test_03(){
        Library library = new Library();

        Boolean validBooks = true;

        for (int i = 0; i < 20 ; ++i){
            Book book = library.getBookByIndex(i);
            if (!validateBook(book)) {
                validBooks = false;
                break;
            }
        }
        assertTrue(validBooks, "All books should have valid data.");
    }
    // Helper for RESP_01_test_03
    private boolean validateBook(Book book) {
        return book != null &&
                book.getTitle() != null &&
                book.getAuthor() != null &&
                !book.getTitle().isEmpty() &&
                !book.getAuthor().isEmpty();
    }

    @Test
    @DisplayName("The library should have 3 borrower accounts.")
    void RESP_02_test_01(){
        Library library = new Library();

        int size = library.getBorrowersSize();
        assertEquals(3, size);
    }

    @Test
    @DisplayName("main.Library should have a specific borrower after initialization - Bob_White")
    void RESP_02_test_02(){
        Library library = new Library();

        Borrower targetBorrower = library.getBorrowerByName("Bob_White");
        String targetBorrowerName = targetBorrower.getUsername();
        assertEquals("Bob_White", targetBorrowerName);
    }

    @Test
    @DisplayName("All borrower accounts should have a username and password")
    void RESP_02_test_03(){
        Library library = new Library();

        Boolean validBorrowers = true;

        for (int i = 0; i < 3; ++i){
            Borrower borrower = library.getBorrowerByIndex(i);
            if (!validateBorrower(borrower)) {
                validBorrowers = false;
                break;
            }
        }
        assertTrue(validBorrowers, "All books should have valid data.");
    }
    // Helper for RESP_01_test_03
    private boolean validateBorrower(Borrower borrower) {
        return borrower != null &&
                borrower.getUsername() != null &&
                borrower.getPassword() != null &&
                !borrower.getUsername().isEmpty() &&
                !borrower.getPassword().isEmpty();
    }

    @Test
    @DisplayName("Upon login, the borrower has no holdings")
    void RESP_06_test_01(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);

        library.login("Bob_White", "Password123");
        ui.displayMainOptions();

        String output = outContent.toString();
        assertTrue(output.contains("You have 0 available holding(s)"));
    }

    @Test
    @DisplayName("Upon login, the borrower has a hold on one book")
    void RESP_06_test_02(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);

        library.login("Bob_White", "Password123");
        library.holdForUser("Bob_White", new Book("book1", "book1"));
        ui.displayMainOptions();

        String output = outContent.toString();
        assertTrue(output.contains("You have 1 available holding(s)"));
    }

    @Test
    @DisplayName("Upon login, the borrower has a hold on two different books, despite attempting to put a hold on the same book twice")
    void RESP_06_test_03(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);

        library.login("Bob_White", "Password123");
        Book book2 = new Book("book2", "book2");
        library.holdForUser("Bob_White", new Book("book1", "book1"));
        library.holdForUser("Bob_White", book2);
        library.holdForUser("Bob_White", book2);
        ui.displayMainOptions();

        String output = outContent.toString();
        System.setOut(System.out);
        System.out.println(output);
        assertTrue(output.contains("You have 2 available holding(s)"));
    }


    @Test
    @DisplayName("Borrower book count should be 0")
    void RESP_08_test_01(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);

        library.login("Bob_White", "Password123");
        ui.borrowOptions();

        String output = outContent.toString();
        assertTrue(output.contains("Books currently borrowed: 0"));
    }

    @Test
    @DisplayName("Borrower book count should say 3")
    void RESP_08_test_02(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);

        Borrower borrower = library.getBorrowerByName("Bob_White");
        borrower.addBook(new Book("book1", "book1"));
        borrower.addBook(new Book("book2", "book2"));
        borrower.addBook(new Book("book3", "book3"));
        library.login("Bob_White", "Password123");
        ui.borrowOptions();

        String output = outContent.toString();
        assertTrue(output.contains("Books currently borrowed: 3"));
    }

    @Test
    @DisplayName("The Great Gatsby should be in the catalogue")
    void RESP_09_test_01(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);

        library.login("Bob_White", "Password123");
        ui.borrowOptions();

        String output = outContent.toString();
        assertTrue(output.contains("The Great Gatsby | Author: F. Scott Fitzgerald"));
    }

    @Test
    @DisplayName("The due date should display a book has one, Threads of Tomorrow on 2025-09-30")
    void RESP_09_test_02(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        library.getBookByTitle("Threads of Tomorrow").setAvailabilityStatus(Book.Status.CHECKED_OUT);
        library.getBookByTitle("Threads of Tomorrow").setDueDate("2025-09-30");

        library.login("Bob_White", "Password123");
        ui.borrowOptions();

        String output = outContent.toString();
        assertTrue(output.contains("2025-09-30"));
    }


}
