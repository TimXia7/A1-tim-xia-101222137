
import main.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {

    @Test
    @DisplayName("main.Library catalogue size should be 20 upon initialization.")
    void RESP_01_test_01(){
        Library library = new Library();
        library.initializeLibrary();

        int size = library.getCatalogueSize();
        assertEquals(20, size);
    }

    @Test
    @DisplayName("main.Library should have a specific book after initialization - The Great Gatsby.")
    void RESP_01_test_02(){
        Library library = new Library();
        library.initializeLibrary();

        Book targetBook = library.getBookByTitle("The Great Gatsby");
        String targetBookTitle = targetBook.getTitle();
        assertEquals("The Great Gatsby", targetBookTitle);
    }

    @Test
    @DisplayName("Ensure that all books in the catalogue have valid data.")
    void RESP_01_test_03(){
        Library library = new Library();
        library.initializeLibrary();

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
        library.initializeBorrowers();

        int size = library.getBorrowersSize();
        assertEquals(3, size);
    }

    @Test
    @DisplayName("main.Library should have a specific borrower after initialization - Bob_White")
    void RESP_02_test_02(){
        Library library = new Library();
        library.initializeBorrowers();

        Borrower targetBorrower = library.getBorrowerByName("Bob_White");
        String targetBorrowerName = targetBorrower.getUsername();
        assertEquals("Bob_White", targetBorrowerName);
    }

    @Test
    @DisplayName("All borrower accounts should have a username and password")
    void RESP_02_test_03(){
        Library library = new Library();
        library.initializeBorrowers();

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



}
