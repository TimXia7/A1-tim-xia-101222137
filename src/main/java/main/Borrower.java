package main;

public class Borrower {
    private String username;
    private String password;
    private String sessionToken;

    private Catalogue books;

    public Borrower(String username, String password) {
        this.username = username;
        this.password = password;
        this.sessionToken = null;
        books = new Catalogue();
    }

    public String getUsername() { return username; }
    public String getPassword() {return password; }
    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public void addBook(Book book){ this.books.addBook(book); }
    public int getBorrowCount(){ return this.books.getSize(); }
    public Book getBorrowedBook(int index){ return this.books.getBook(index); }
}
