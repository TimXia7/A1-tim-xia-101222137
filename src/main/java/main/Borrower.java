package main;

public class Borrower {
    private String username;
    private String password;
    private String sessionToken;

    public Borrower(String username, String password) {
        this.username = username;
        this.password = password;
        this.sessionToken = null;
    }

    public String getUsername() { return username; }
    public String getPassword() {return password; }
    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }
}
