package LibraryStates;

import main.Library;
import main.LibraryUI;

public class LoginState implements LibraryState {
    @Override
    public void run(Library library, LibraryUI ui) {
        String[] credentials = ui.promptCredentials();
        String username = credentials[0];
        String password = credentials[1];

        String token = library.login(username, password);

        if (token != null) {
            ui.displayMessage("Login successful!");
            ui.setState(new MainMenuState());
        } else {
            ui.displayMessage("Login failed, try again.");
            ui.setState(this);
        }
    }
}
