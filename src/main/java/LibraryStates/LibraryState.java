package LibraryStates;
import main.Library;
import main.LibraryUI;

public interface LibraryState {
    void run(Library context, main.LibraryUI ui);
}
