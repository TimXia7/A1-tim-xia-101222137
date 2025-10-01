package LibraryStates;

import main.Book;
import main.Library;
import main.LibraryUI;

// ran after logout, or when needed in testing
public class ConfirmBorrowState implements LibraryState {
    @Override
    public void run(Library library, LibraryUI ui) {
        int choice = ui.confirmBorrowOperation(ui.getBorrowTarget());

        switch (choice) {
            case 1:
                ui.setState(new ShutdownState());
                // Future book borrow processing should be here
                break;
            case 2:
                ui.setState(new BorrowState());
                break;
            default:
                ui.setState(this);
                break;
        }
    }
}
