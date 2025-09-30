package LibraryStates;

import main.Library;
import main.LibraryUI;

// ran after logout, or when needed in testing
public class ShutdownState implements LibraryState {
    @Override
    public void run(Library library, LibraryUI ui) {
        ui.stop();
    }
}
