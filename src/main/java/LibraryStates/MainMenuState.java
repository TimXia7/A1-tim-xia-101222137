package LibraryStates;
import main.Library;
import main.LibraryUI;

public class MainMenuState implements LibraryState {
    public void run(Library context, LibraryUI ui) {
        int choice = ui.mainOptions();

        switch (choice) {
            case 1:
                ui.setState(new BorrowState());
                break;
            case 2:
                ui.setState(new ShutdownState());
                break;
            case 0:
                ui.setState(new ShutdownState());
                break;
            default:
                ui.setState(this);
                break;
        }

    }
}
