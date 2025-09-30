package LibraryStates;
import main.Library;
import main.LibraryUI;

public class MainMenuState implements LibraryState {
    public void run(Library context, LibraryUI ui) {
        int choice = ui.mainOptions();

        // switch(choice) = determine next state
        ui.setState(new ShutdownState());
    }
}
