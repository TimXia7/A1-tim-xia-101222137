package main;

import java.util.UUID;
import java.util.regex.Pattern;

public class SecurityManager {

    private final String regex;
    private Library library;

    public SecurityManager(Library library, String regex) {
        this.regex = regex;
        this.library = library;
    }

    public String login(String username, String password) {
        // First validate
        int status = this.validate(username, password);

        if (status != 0) {
            return null;
        }

        // main.Borrower is valid, assign a session token
        library.initializeBorrowers();

        Borrower borrower = library.getBorrowerByName(username);
        String token = UUID.randomUUID().toString();
        borrower.setSessionToken(token);

        return token; // return token to caller
    }

    // 0 = successful validate
    // 1 = regex error
    // 2 = does not match existing entry
    public int validate(String username, String password) {
        // 1. Check regex
        if (password == null || !Pattern.matches(regex, password)) {
            return 1;
        }

        // 2. check existing entry
        library.initializeBorrowers();

        Borrower borrower = library.getBorrowerByName(username);
        if (borrower == null || !borrower.getPassword().equals(password)) {
            return 2;
        }

        return 0;
    }
}
