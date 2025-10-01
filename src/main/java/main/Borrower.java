package main;

public class Borrower {
    private String username;
    private String password;
    private String sessionToken;

    private Catalogue books;
    private Catalogue holdings;

    public Borrower(String username, String password) {
        this.username = username;
        this.password = password;
        this.sessionToken = null;
        books = new Catalogue();
        holdings = new Catalogue();
    }

    public String getUsername() { return username; }
    public String getPassword() {return password; }
    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public void addBook(Book book){ this.books.addBook(book); }
    public int getBorrowCount(){ return this.books.getSize(); }
    public Book getBorrowedBook(int index){ return this.books.getBook(index); }

    public void addHolding(Book book) { this.holdings.addBook(book); }
    public int getHoldingCount(){ return this.holdings.getSize(); }
    public Book getHolding(int index) { return holdings.getBook(index); }
}
