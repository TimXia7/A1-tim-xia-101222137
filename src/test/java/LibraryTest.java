
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {

    @Test
    @DisplayName("Library catalogue size should be 20 upon initialization.")
    void RESP_01_test_01(){
        Library library = new Library();
        library.initializeLibrary();

        int size = library.getCatalogueSize();
        assertEquals(20, size);
    }

    @Test
    @DisplayName("Library should have a specific book after initialization - The Great Gatsby.")
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

        Boolean validBooks = false;

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

}
