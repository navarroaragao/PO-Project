package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import java.util.List;


class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("userId", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        
        try {
            List<String> notifications = _receiver.getLibrary().showUserNotifications(userId);
            
            for (String notification : notifications) {
                _display.addLine(notification);
            }
            
        } catch (bci.exceptions.NoSuchUserException e) {
            throw new NoSuchUserException(userId);
        }
    }

}
