import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Library {
    private Catalogue catalogue;
    private BorrowerList borrowerList;

    public Library() {
        catalogue = new Catalogue();
        borrowerList = new BorrowerList();
    }

    public void initializeLibrary() {
        try (InputStream input = getClass().getResourceAsStream("/books.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    catalogue.addBook(new Book(title, author));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeBorrowers() {
        try (InputStream input = getClass().getResourceAsStream("/borrowers.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    borrowerList.addBorrower(new Borrower(username, password));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public int getCatalogueSize(){ return catalogue.getSize(); }
    public Book getBookByTitle(String title){
        Book book;
        for (int i = 0 ; i < catalogue.getSize(); ++i){
            book = catalogue.getBook(i);
            if (Objects.equals(book.getTitle(), title)){
                return book;
            }
        }
        return null;
    }
    public Book getBookByIndex(int index) {
        if (index < 0 || index >= catalogue.getSize()) {
            return null;
        }
        return catalogue.getBook(index);
    }
    public int getBorrowersSize() { return borrowerList.getSize(); }
    public Borrower getBorrowerByName(String username){
        Borrower borrower;
        for (int i = 0 ; i < borrowerList.getSize(); ++i){
            borrower = borrowerList.getBorrower(i);
            if (Objects.equals(borrower.getUsername(), username)){
                return borrower;
            }
        }
        return null;
    }
    public Borrower getBorrowerByIndex(int index) {
        if (index < 0 || index >= borrowerList.getSize()) {
            return null;
        }
        return borrowerList.getBorrower(index);
    }


}
