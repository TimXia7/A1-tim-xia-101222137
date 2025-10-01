package LibraryStates;

import main.Book;
import main.Library;
import main.LibraryUI;

// ran after logout, or when needed in testing
public class BorrowState implements LibraryState {
    @Override
    public void run(Library library, LibraryUI ui) {
        if (library.getActiveUser().getSessionToken() == null){
            ui.setState(new MainMenuState());
        }

        ui.borrowOptions();

        Book book = ui.handleBorrowOperation();
        if (book == null) {
            ui.setState(this);
        } else {
            ui.setState(new ConfirmBorrowState());
        }
    }
}
