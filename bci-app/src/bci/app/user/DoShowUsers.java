package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import java.util.List;

/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

    DoShowUsers(LibraryManager receiver) {
        super(Label.SHOW_USERS, receiver);
    }

    @Override
    protected final void execute() {
        List<String> users = _receiver.getLibrary().showUsers();
        for (String user : users) {
            _display.addLine(user);
        }
    }

}
