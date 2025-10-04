
import main.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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
    @DisplayName("Ensure that all books in the catalogue are not null after init")
    void RESP_01_test_03() {
        Library library = new Library();

        boolean validBooks = true;
        for (int i = 0; i < 20; ++i) {
            Book book = library.getBookByIndex(i);
            if (book == null) {
                validBooks = false;
                break;
            }
        }
        assertTrue(validBooks);
    }

    @Test
    @DisplayName("Ensure that all books in the catalogue have non-empty titles")
    void RESP_01_test_04() {
        Library library = new Library();

        boolean validTitles = true;
        for (int i = 0; i < 20; ++i) {
            Book book = library.getBookByIndex(i);
            if (book.getTitle() == null || book.getTitle().isEmpty()) {
                validTitles = false;
                break;
            }
        }
        assertTrue(validTitles);
    }

    @Test
    @DisplayName("Ensure that all books in the catalogue have non-empty authors")
    void RESP_01_test_05() {
        Library library = new Library();

        boolean validAuthors = true;
        for (int i = 0; i < 20; ++i) {
            Book book = library.getBookByIndex(i);
            if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
                validAuthors = false;
                break;
            }
        }
        assertTrue(validAuthors);
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
    @DisplayName("All borrower accounts should not be null")
    void RESP_02_test_03() {
        Library library = new Library();

        boolean validBorrowers = true;
        for (int i = 0; i < 3; ++i) {
            Borrower borrower = library.getBorrowerByIndex(i);
            if (borrower == null) {
                validBorrowers = false;
                break;
            }
        }
        assertTrue(validBorrowers);
    }

    @Test
    @DisplayName("All borrower accounts should have a non-empty username")
    void RESP_02_test_04() {
        Library library = new Library();

        boolean validUsernames = true;
        for (int i = 0; i < 3; ++i) {
            Borrower borrower = library.getBorrowerByIndex(i);
            if (borrower.getUsername() == null || borrower.getUsername().isEmpty()) {
                validUsernames = false;
                break;
            }
        }
        assertTrue(validUsernames);
    }

    @Test
    @DisplayName("All borrower accounts should have a non-empty password")
    void RESP_02_test_05() {
        Library library = new Library();

        boolean validPasswords = true;
        for (int i = 0; i < 3; ++i) {
            Borrower borrower = library.getBorrowerByIndex(i);
            if (borrower.getPassword() == null || borrower.getPassword().isEmpty()) {
                validPasswords = false;
                break;
            }
        }
        assertTrue(validPasswords);
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

    @Test
    @DisplayName("Querying a borrow for the 1st book, The Great Gatsby should return that book")
    void RESP_10_test_01(){
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        library.login("Bob_White", "Password123");
        Book book = ui.queryBorrow(1);

        assertEquals("The Great Gatsby", book.getTitle());
    }

    @Test
    @DisplayName("Querying a borrow for the 21st book, which doesn't exist (should be null)")
    void RESP_10_test_02(){
        Library library = new Library();
        LibraryUI ui = new LibraryUI(library);
        library.login("Bob_White", "Password123");
        Book book = ui.queryBorrow(21);

        assertNull(book);
    }

    @Test
    @DisplayName("A book that is checked out should not be valid")
    void RESP_11_test_01(){
        Library library = new Library();
        Book book = library.getBookByTitle("The Great Gatsby");
        book.setAvailabilityStatus(Book.Status.CHECKED_OUT);

        book = library.verifyBookEligibility(book);

        assertNull(book);
    }

    @Test
    @DisplayName("A book that is on hold should not be valid")
    void RESP_11_test_02(){
        Library library = new Library();
        Book book = library.getBookByTitle("The Great Gatsby");
        book.setAvailabilityStatus(Book.Status.ON_HOLD);

        book = library.verifyBookEligibility(book);

        assertNull(book);
    }

    @Test
    @DisplayName("A book that is available should be valid")
    void RESP_11_test_03(){
        Library library = new Library();
        Book book = library.getBookByTitle("The Great Gatsby");
        book.setAvailabilityStatus(Book.Status.AVAILABLE);

        Book book2 = library.verifyBookEligibility(book);

        assertEquals(book, book2);
    }

    @Test
    @DisplayName("A borrower that has no books borrowed should be valid")
    void RESP_12_test_01(){
        Library library = new Library();
        Borrower borrower = library.getBorrowerByName("Bob_White");
        Borrower borrower2 = library.verifyBorrowerEligibility(borrower);

        assertEquals(borrower, borrower2);
    }

    @Test
    @DisplayName("A borrower that has two books borrowed should be valid")
    void RESP_12_test_02(){
        Library library = new Library();
        Borrower borrower = library.getBorrowerByName("Bob_White");
        borrower.addBook(new Book("", ""));
        borrower.addBook(new Book("", ""));

        Borrower borrower2 = library.verifyBorrowerEligibility(borrower);

        assertEquals(borrower, borrower2);
    }

    @Test
    @DisplayName("A borrower that has three books borrowed should not be valid")
    void RESP_12_test_03(){
        Library library = new Library();
        Borrower borrower = library.getBorrowerByName("Bob_White");
        borrower.addBook(new Book("", ""));
        borrower.addBook(new Book("", ""));
        borrower.addBook(new Book("", ""));

        borrower = library.verifyBorrowerEligibility(borrower);

        assertNull(borrower);
    }

    @Test
    @DisplayName("See if a basic 14 day due date is calculated correctly.")
    void RESP_13_test_01(){
        Library library = new Library();
        Book book = library.getBookByTitle("The Great Gatsby");

        // library: initialDate = 2025-10-01
        library.calculateDueDate(book);

        String dueDate = book.getDueDate();
        assertEquals("2025-10-15", dueDate);
    }

    @Test
    @DisplayName("See if a 14 day due date is calculated correctly over a year")
    void RESP_13_test_02(){
        Library library = new Library();
        Book book = library.getBookByTitle("The Great Gatsby");

        library.setDate("2025-12-29");
        library.calculateDueDate(book);

        String dueDate = book.getDueDate();
        assertEquals("2026-01-12", dueDate);
    }

    @Test
    @DisplayName("See if a 14 day due date is calculated correctly for a leap year")
    void RESP_13_test_03(){
        Library library = new Library();
        Book book = library.getBookByTitle("The Great Gatsby");

        // (leap day)
        library.setDate("2024-02-29");
        library.calculateDueDate(book);

        String dueDate = book.getDueDate();
        assertEquals("2024-03-14", dueDate);
    }
}
