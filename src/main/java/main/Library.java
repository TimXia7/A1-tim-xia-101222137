package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Library {
    private Catalogue catalogue;
    private BorrowerList borrowerList;
    private final SecurityManager securityManager;

    public Library() {
        catalogue = new Catalogue();
        borrowerList = new BorrowerList();
        securityManager = new SecurityManager(this, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

        initializeLibrary();
        initializeBorrowers();
    }

    public void initializeLibrary() {
        catalogue.clear();
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
        borrowerList.clear();

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

    public void holdForUser(String username, Book book) {
        Borrower borrower = getBorrowerByName(username);

        boolean alreadyHolding = false;
        for (int i = 0; i < borrower.getHoldingCount(); i++) {
            if (borrower.getHolding(i).equals(book)) {
                alreadyHolding = true;
                System.out.println("Error in Holding Operation: User already has a holding on this book.");
                break;
            }
        }

        if (!alreadyHolding) {
            book.addHolder(borrower);
            borrower.addHolding(book);

            // If the book is AVAILABLE, then mark it as ON_HOLD
            // Another check occurs when a book is returned
            if (book.getAvailabilityStatus() == Book.Status.AVAILABLE) {
                book.setAvailabilityStatus(Book.Status.ON_HOLD);
            }
        }
    }

    public Book verifyBookEligibility(Book book) {
        if (book == null) {
            System.out.println("Invalid book cannot be verified.");
            return null;
        }

        return switch (book.getAvailabilityStatus()) {
            case ON_HOLD -> {
                System.out.println("This book is currently on hold.");
                yield null;
            }
            case CHECKED_OUT -> {
                System.out.println("This book is already checked out.");
                yield null;
            }
            default -> book;
        };
    }

    public Borrower verifyBorrowerEligibility(Borrower borrower) {
        int borrowCount = borrower.getBorrowCount();
        if (borrowCount >= 3) {
            System.out.println("User has hit the book borrowing limit (3).");
            return null;
        } else {
            return borrower;
        }
    }




    // Validation wrapper:
    // returns borrower token
    public String login(String username, String password) {
        return securityManager.login(username, password);
    }
    public String logout(String username) { return securityManager.logout(username); }


    // Getters and setters
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

    // The logic of this will be much more complicated with multiple users at once
    public Borrower getActiveUser() {
        for (int i = 0; i < borrowerList.getSize(); i++) {
            Borrower borrower = borrowerList.getBorrower(i);
            if (borrower.getSessionToken() != null) {
                return borrower;
            }
        }
        return null;
    }
}
