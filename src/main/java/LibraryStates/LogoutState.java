package LibraryStates;

import main.Library;
import main.LibraryUI;

public class LogoutState implements LibraryState {
    @Override
    public void run(Library library, LibraryUI ui) {
        int choice = ui.confirmLogout();

        switch (choice) {
            case 1:
                ui.setState(new ShutdownState());
                break;
            case 2:
                ui.setState(new MainMenuState());
                break;
            default:
                ui.setState(this);
                break;
        }
    }
}
