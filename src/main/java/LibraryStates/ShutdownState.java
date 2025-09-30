package LibraryStates;

import main.Library;
import main.LibraryUI;

// ran after logout, or when needed in testing
public class ShutdownState implements LibraryState {
    @Override
    public void run(Library library, LibraryUI ui) {
        System.out.println("Library system shutting down...");
        ui.stop();
    }
}
